package study.reactiveservice.service

import org.springframework.stereotype.Service
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
}