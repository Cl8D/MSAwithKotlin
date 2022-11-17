package study.basicService.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import study.basicService.model.Customer
import java.util.concurrent.ConcurrentHashMap

@RestController
class CustomerSampleController {

    // 이런 식으로 바로 반환이 가능하다.
    @GetMapping("/sample")
    fun getSample() = "Hello I'm Sample Controller"

    @GetMapping("/sample-customer")
    fun getSampleCustomer() = Customer(1, "Hello",
            Customer.Telephone("+010", "12345678"))

    // lateinit를 붙여주면 변수 초기화를 미룰 수 있다.
    // 생성자 다음에 초기화를 하는 것과 동일하다.
    // 동일한 타입의 결과값을 가져왔다고 볼 수 있다.
    @Autowired
    lateinit var customerList : ConcurrentHashMap<Long, Customer>

    /** @Autowired Basic */
    @GetMapping("/sample-customer-idx")
    fun getSampleCustomerIdx() = customerList[2];

    /** @PathVariable Basic - 고객 번호로 고객 조회*/
    @GetMapping("/sample-customer/{id}")
    fun getSampleCustomerPath(@PathVariable id : Long) = customerList[id];

    /** 고객 목록을 반환하는 메서드 */
    @GetMapping("/sample-customers")
    fun getSampleCustomerList() = customerList.map(Map.Entry<Long, Customer>::value).toList()

    /** 고객 목록의 일부분을 필터링 하기 */
    // /customers-filtiering?nameFilter=1
    // 'hel'이라는 이름을 가진 고객을 필터링하기
    @GetMapping("/sample-customers-filtering")
    fun getSampleCustomerListFilter(@RequestParam(required = false, defaultValue = "") nameFilter: String)
            // Map에서 filter 기능을 통해 각각의 값(it)을 순회하면서 name 요소의 값이 쿼리 파라미터로 들어온 값이 포함되는지(contains) 판단한다.
            // 이때, ignoreCase=true 옵션을 통해 대/소문자를 무시하도록 설정이 가능하다.
            = customerList.filter {
                it.value.name.contains(nameFilter, true)
            }.map(Map.Entry<Long, Customer>::value).toList()
    /*
        마지막에 .map.toList()를 하지 않았을 때 결과를 보자.
        {
          "1": {
            "id": 1,
            "name": "Hello1"
          }
        }

        --

        .map.toList() 했을 때의 결과
        [
          {
            "id": 1,
            "name": "Hello1"
          }
        ]

        --

        단순히 .toList() 했을 때의 결과
        [
          {
            "first": 1,
            "second": {
              "id": 1,
              "name": "Hello1"
            }
          }
        ]
     */

    /** 고객 등록을 하는 메서드 */
    @PostMapping("/sample-customer")
    fun createCustomer(@RequestBody customer: Customer) {
        customerList[customer.id] = customer
    }

    /** 고객 삭제하는 메서드 */
    @DeleteMapping("/sample-customer/{id}")
    fun deleteSampleCustomer(@PathVariable id : Long) = customerList.remove(id)

    /** 고객 업데이트 메서드 */
    @PutMapping("/sample-customer/{id}")
    fun updateSampleCustomer(@PathVariable id : Long, @RequestBody customer: Customer) {
        customerList.remove(id)
        customerList[customer.id] = customer
    }

}