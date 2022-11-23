package study.reactiveservice.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.kotlin.core.publisher.toMono
import study.reactiveservice.domain.Customer
import study.reactiveservice.service.CustomerService

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
    
}