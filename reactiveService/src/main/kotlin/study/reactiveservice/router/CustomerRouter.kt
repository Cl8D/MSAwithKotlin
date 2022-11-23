package study.reactiveservice.router

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.router
import reactor.kotlin.core.publisher.toMono
import study.reactiveservice.domain.Customer
import study.reactiveservice.handler.CustomerHandler

@Component
// private final = private val(불변), 이런 식으로 생성자 주입을 사용할 수 있다.
class CustomerRouter(private val customerHandler: CustomerHandler) {

    /**
     * 마이크로서비스로 들어오는 요청을 어떻게 처리할지 정의하기 위해 RouterFunction 정의.
     */
    @Bean
    fun tempCustomerRoutes() : RouterFunction<*> = router {
        // /functional으로 들어온 모든 요청을 처리하게 된다.
        "/functional".nest {
            // 한 단계 더 들어가서, /functional/customer/로 들어온 요청에 대해서 'Hello world'를 응답하게 된다.
            "/customer".nest {
                GET("/") {
                    // 여기서 ServerResponse.ok()는 ServerResponse.Builder를 통하여 Mono<ServerResponse>를 만들고,
                    // 해당 응답값에는 Mono<String>이 들어있게 된다.
                    // 즉, Mono<ServerResponse> 객체에는 'Hello World' 문자열이 포함된 Mono<String>이 들어 있다.
//                    ServerResponse.ok().body("Hello World!".toMono(),
//                        String::class.java)

                    // 코틀린의 타입 추론 기능을 활용하면 다음과 같이 줄일 수 있다.
                    ok().body("Hello World!".toMono(), String::class.java)
                }
            }
        }
    }

    @Bean
    fun customerRoutesVer2() : RouterFunction<*> = router {
        "/functional".nest {
            "/customer".nest {
                GET("/ver2") {
                    // it은 ServerRequest의 객체로, 매개변수나 요청 본문을 포함한 핸들러로 보낼 모든 세부 정보가 포함된다.
                    it : ServerRequest ->
                        ok().body(Customer(1, "Router Version 2").toMono(),
                        Customer::class.java)
                }
            }
        }
    }

    /** ver2를 handler와 router 구조로 분해하여 표현한 것이다. */
    @Bean
    fun customerRouterVer3() : RouterFunction<*> = router {
        "/functional".nest {
            "/customer".nest {
                GET("/ver3") {
                    it : ServerRequest -> customerHandler.tempGet(it)
                }
            }
        }
    }

    /** ver3을 람다식을 활용하여 줄인 버전이다. */
    @Bean
    fun customerRouterVer4() : RouterFunction<*> = router {
        "/functional".nest {
            "/customer".nest {
                GET("/ver4", customerHandler::tempGet)
            }
        }
    }


    /** REST API에 적용하기. */
    @Bean
    fun customerRoutes() : RouterFunction<*> = router {
        "/functional".nest {
            "/customer".nest {
                GET("/{id}", customerHandler::getCustomer)
            }
        }
    }

}