package study.reactiveservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import study.reactiveservice.domain.Customer
import study.reactiveservice.service.CustomerService

@RestController
@RequestMapping("/customer")
class CustomerController {

    @Autowired
    private lateinit var customerService: CustomerService

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Long) : ResponseEntity<Customer> {
        val customer = customerService.getCustomer(id)
        return ResponseEntity(customer, HttpStatus.OK)
    }

    @GetMapping("/reactive/{id}")
    fun getCustomerByR(@PathVariable id: Long) : ResponseEntity<Mono<Customer>> {
        val customer = customerService.getCustomerByR(id)
        return ResponseEntity(customer, HttpStatus.OK)
    }

    // http://localhost:8080/customer/search?nameFilter=micro
    @GetMapping("/search")
    fun searchCustomer(@RequestParam(required = false, defaultValue = "") nameFilter: String) =
            customerService.searchCustomer(nameFilter)

    @PostMapping("/reactive")
    fun createCustomer(@RequestBody customerMono: Mono<Customer>) =
            ResponseEntity(customerService.createCustomerByR(customerMono), HttpStatus.CREATED)
}