package study.basicService.service

import org.springframework.stereotype.Component
import study.basicService.model.Customer
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl : CustomerService {
    companion object {
        val initialCustomers = arrayOf(
                Customer(1, "Hello1"),
                Customer(2, "Hello2"),
                Customer(3, "Hello3"))
    }

    // 서로 다른 요청이 맵의 동일한 요소에 액세스 시 동기화 문제가 발생할 수 있기 때문에 concurrentHashMap 사용
    // associateBy를 활용하면 프로퍼티를 Key-value 형태로 묶어줄 수 있다.
    val customerList = ConcurrentHashMap<Long, Customer>(initialCustomers.associateBy(Customer::id))

    override fun getCustomer(id: Long) = customerList[id]

    override fun createCustomer(customer: Customer) {
        customerList[customer.id] = customer
    }

    override fun deleteCustomer(id: Long) {
        customerList.remove(id)
    }

    override fun updateCustomer(id: Long, customer: Customer) {
        deleteCustomer(id)
        createCustomer(customer)
    }

    override fun searchCustomers(nameFilter: String): List<Customer> =
        customerList.filter {
            it.value.name.contains(nameFilter, true)
        }.map(Map.Entry<Long, Customer>::value).toList()
}