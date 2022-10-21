package study.basicService

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import study.basicService.model.Customer
import java.util.concurrent.ConcurrentHashMap

@SpringBootApplication
class CustomerApplication {
    companion object {
        val initialCustomers = arrayOf(
                Customer(1, "Hello1"),
                Customer(2, "Hello2"),
                Customer(3, "Hello3"))
    }

    // 서로 다른 요청이 맵의 동일한 요소에 액세스 시 동기화 문제가 발생할 수 있기 때문에 concurrentHashMap 사용
    // associateBy를 활용하면 프로퍼티를 Key-value 형태로 묶어줄 수 있다.
    @Bean
    fun customers() = ConcurrentHashMap<Long, Customer>(initialCustomers.associateBy(Customer::id))

}