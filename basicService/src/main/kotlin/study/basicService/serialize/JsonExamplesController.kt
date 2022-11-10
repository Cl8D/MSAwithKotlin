package study.basicService.serialize

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JsonExamplesController {
    /*
        {
          "name": "hello"
          "place": "world"
        }
     */
    @GetMapping("/json")
    fun getJson() = SimpleObject()

    /** using Data Class */
    @GetMapping("/json2")
    fun getJson2() = SimpleObject2("hi", "kotlin!")

    /** Complex Example */
    // 여기서 ComplexObject가 데이터 클래스이기 때문에 getObject 메서드를 가지게 되고,
    // ObjectMapper가 해당 객체를 object1의 속성으로 deserialize 해준다.
    /*
        {
          "object1": {
            "name": "complex",
            "place": "example"
          }
        }
     */
    @GetMapping("/json3")
    fun getJson3() = ComplexObject(object1 = SimpleObject2("complex", "example"))


}