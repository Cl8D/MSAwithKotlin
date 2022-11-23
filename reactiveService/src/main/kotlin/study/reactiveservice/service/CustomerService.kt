package study.reactiveservice.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import study.reactiveservice.domain.Customer

interface CustomerService {
    fun getCustomer(id: Long) : Customer?
    fun searchCustomer(nameFilter: String) : List<Customer>

    /**
     * 객체에 대해 리액티브하게 반환하기.
     */
    /** 모노 사용 */
    // 여기서 Mono<>는 실제 인스턴스가 아니라, 앞으로 우리가 얻으려고 하는 것에 대한 일종의 '약속'으로 동작한다.
    // 즉, Mono<>로 선언하면, 이 publisher는 앞으로 Customer를 게시할 것을 약속하게 된다.
    fun getCustomerByR(id: Long) : Mono<Customer>?

    /** 플럭스 사용 */
    // flux를 통해 0~무한대의 요소를 가진 publisher를 만들 수 있다.
    // 모노와 마찬가지로 publish를 했을 때 실행될 수 있다는 일종의 '약속'으로 동작한다.
    // 기본형으로는 Flux.fromIterable(listOf(Customer(1, "Hi"), Customer(2, "Kotlin")))와 같이 선언할 수 있다.
    // 조금 코틀린에서는 축악하여 listOf(Customer(1, "Hi"), Customer(2, "Kotlin")).toFlux()를 활용할 수 있다.
    fun searchCustomerByR(nameFilter: String) : Flux<Customer>
}