package study.basicService.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import study.basicService.model.Customer
import study.basicService.service.CustomerService

@RestController
class CustomerController {
    @Autowired
    private lateinit var customerService: CustomerService

    /** ResponseEntity를 통해 상태코드 지정하기 */
    @GetMapping("/customer")
    fun getCustomer(@PathVariable id: Long) : ResponseEntity<Customer?> {
        val customer = customerService.getCustomer(id)
        // 찾지 못했을 때 404, 조회 성공 시 200 내려주기
        val status = if (customer == null) HttpStatus.NOT_FOUND else HttpStatus.OK

        return ResponseEntity(customer, status)
    }

    /** Unit type = 일종의 void 같은 역할이다. */
    // Unit? -> null일 경우 {}이 반환되지 않고 아예 반환되는 내용이 없도록 수정.
    @PostMapping("/customer")
    fun createCustomer(@RequestBody customer : Customer) : ResponseEntity<Unit?> {
        customerService.createCustomer(customer)

        return ResponseEntity(Unit, HttpStatus.CREATED)
    }

    @DeleteMapping("/customer/{id}")
    fun deleteCustomer(@PathVariable id : Long) : ResponseEntity<Unit> {
        var status = HttpStatus.NOT_FOUND
        // 고객이 있는지 1차적으로 확인하고 존재한다면 삭제 진행 -> 상태 변경.
        if (customerService.getCustomer(id) != null) {
            customerService.deleteCustomer(id)
            status = HttpStatus.OK
        }
        return ResponseEntity(Unit, status)
    }

    @PutMapping("/customer/{id}")
    fun updateCustomer(@PathVariable id : Long, @RequestBody customer: Customer) : ResponseEntity<Unit> {
        var status = HttpStatus.NOT_FOUND
        if (customerService.getCustomer(id) != null) {
            customerService.updateCustomer(id, customer)
            // 리소스를 수정했을 때는 성공 시 202 응답.
            status = HttpStatus.ACCEPTED
        }

        return ResponseEntity(Unit, status)
    }

    @GetMapping("/customers")
    fun getCustomers(@RequestParam(required = false, defaultValue = "") nameFilter : String) =
            customerService.searchCustomers(nameFilter)
}