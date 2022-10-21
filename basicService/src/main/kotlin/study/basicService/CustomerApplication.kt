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

    @Bean
    fun customers() = ConcurrentHashMap<Long, Customer>(initialCustomers.associateBy(Customer::id))

}