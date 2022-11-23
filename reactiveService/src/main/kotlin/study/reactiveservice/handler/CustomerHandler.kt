package study.reactiveservice.handler

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.kotlin.core.publisher.toMono
import study.reactiveservice.domain.Customer
import study.reactiveservice.service.CustomerService
import java.net.URI

@Component
class CustomerHandler(private val customerService: CustomerService) {

    // Handler - Router 구조로 분해하여 표현하기.
    fun tempGet(serverRequest: ServerRequest) =
            ServerResponse.ok().body(Customer(1, "Handler-Router").toMono(),
                    Customer::class.java)


    // 기존에는 Mono<Customer>?를 반환하도록 하였으나, nullable의 경우 Customer::class.java에 바인딩이 불가능하기 때문에
    // null 값이 넘어오지 않도록 별도로 처리하였다.
    // 또한, 넘어온 경로변수를 받기 위해서 serverRequest.pathVariable를 활용한다.
    fun getCustomer(serverRequest: ServerRequest) =
            ServerResponse
                    .ok()
                    .body(customerService
                            .getCustomerByR(serverRequest.pathVariable("id").toLong()),
                            Customer::class.java)


    /** Mono를 활용하여 반환하도록 변경한다.*/
    // 1. Mono<Customer>를 구독하고 있으며, getCustomerByR에 의해서 반환된다.
    // 2. Mono<Customer>에 값이 있으면, it 내부의 Customer 객체를 받게 된다.
    // 2-1. 값이 없다면 notFound (Http 상태 코드)를 지정한다.
    // 3, fromValue를 통해 새로운 Mono<Customer>를 만들어서 반환받는다.
    // 4. 생성한 Mono<Customer>를 활용하여 Mono<ServerResponse>를 생성하여 반환한다.
    // -> 그러면, Mono<ServerResponse>를 구독하고 있다면 해당 subscriber는 Customer 객체를 받게 된다.
    fun getCustomerByMono(serverRequest: ServerRequest) =
            customerService.getCustomerByR(
                    serverRequest.pathVariable("id").toLong())
                    // flatMap을 활용하여 응답으로 변환할 Mono를 얻는다.
                    // 이때 내부에서 Mono를 구독하고 있다가, 값이 존재하면 ok().body()를 통해 Mono<ServerResponse>를 생성한다.
                    .flatMap {
                        ServerResponse
                                .ok()
                                // fromValue를 활용하여 내부의 Customer형을 가져온다고 생각하면 된다.
                                .body(BodyInserters.fromValue(it))
                    }
                    // 본문이 비어있다면, 404 리턴 후 build를 통해 응답 완료 처리. (BodyBuilder 타입이기 때문에 build로 처리가 필요하다)
//                    .switchIfEmpty(ServerResponse.notFound().build())
                    // 같은 내용이지만 이런 식으로 status() 메서드를 활용할 수도 있다.
                    .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build())


    /** 검색을 위한 핸들러 추가. */
    fun searchCustomerByMono(serverRequest: ServerRequest) =
            ServerResponse.ok().body(
                    customerService.searchCustomerByR(
                            // 쿼리 파라미터 처리는 다음과 같이 진행할 수 있다.
                            serverRequest.queryParam("nameFilter")
                                    // 파라미터 값이 비어있을 때도 검색을 진행해야 되기 때문에 (전체 리스트 반환) 빈값으로 처리해준다.
                                    .orElse("")), Customer::class.java)


    /** 생성을 위한 핸들러 추가. */
    fun createCustomerByMono(serverRequest: ServerRequest) =
            customerService.createCustomerByR(
                    // bodyToMono를 활용하여 body로 넘어온 값을 mono로 만들 수 있다.
                    serverRequest.bodyToMono())
                    // flatMap을 통해 response를 mono로 만들어준다. 생성된 Customer를 response로 넘겨준다.
                    .flatMap {
//                        ServerResponse.status(HttpStatus.CREATED)
//                                .body(BodyInserters.fromValue(it))

                        // 마찬가지로 status() 대신에 created() 같은 메서드를 사용할 수는 있다.
                        // 단, 사용하려면 리소스의 URI가 필요하다.
                        // 이렇게 {it.id}를 활용하여 이전의 리소스 id를 함께 넘겨준다.
                            ServerResponse.created(
                                    URI.create("/functional/customer/{it.id}"))
                                    .build()
                    }
}