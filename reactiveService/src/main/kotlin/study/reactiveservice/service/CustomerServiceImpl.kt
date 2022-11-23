package study.reactiveservice.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import study.reactiveservice.domain.Customer
import study.reactiveservice.domain.Customer.*
import java.util.concurrent.ConcurrentHashMap

@Service
class CustomerServiceImpl : CustomerService {

    companion object {
        val initialCustomers = arrayOf(
                Customer(1, "Kotlin"),
                Customer(2, "Spring"),
                Customer(3, "MicroService", Telephone("+82", "12345678")))
    }

    // 비동기 통신을 위해서 concurrentHashMap 사용.
    // associateBy -> Customer의 Id값을 key값으로 하고, Customer를 value로 가지는 맵을 생성해준다.
    val customers = ConcurrentHashMap<Long, Customer>(
            initialCustomers.associateBy(Customer::id))

    override fun getCustomer(id: Long): Customer? = customers[id]

    override fun searchCustomer(nameFilter: String): List<Customer> =
            customers.filter {
                it.value.name.contains(nameFilter, true)
            }.map(Map.Entry<Long, Customer>::value).toList()


    // 여기서는 코틀린의 타입 추론 기능을 위해서 .toMono를 사용하였다.
    // Mono<Customer> = Customer(1, "Mono").toMono와 동일한 의미이다.
    // 조금 더 풀어쓰면, Mono<Customer> = Mono.just(Customer(1, "Mono")와 동일한 의미이다.
    override fun getCustomerByR(id: Long): Mono<Customer>? =
            customers[id]?.toMono()

    override fun searchCustomerByR(nameFilter: String): Flux<Customer> =
            customers.filter {
                it.value.name.contains(nameFilter, true)
            }.map(Map.Entry<Long, Customer>::value).toFlux()
}