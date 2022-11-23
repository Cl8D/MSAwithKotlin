package study.reactiveservice.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.kotlin.core.publisher.toMono
import study.reactiveservice.domain.Customer

@Component
class CustomerHandler {

    // Handler - Router 구조로 분해하여 표현하기.
    fun get(serverRequest: ServerRequest) =
            ServerResponse.ok().body(Customer(1, "Handler-Router").toMono(),
                Customer::class.java)
}