package study.basicService.model

import com.fasterxml.jackson.annotation.JsonInclude

/** cf) Tools -> kotlin -> show Kotlin Bytecode -> Decompile 클릭 시 자바 코드로 변환된 거 확인 가능 */
/** Kotlin Data Class */
// 데이터의 보관 목적으로 만들 수 있는 클래스.
// 자동으로 toString(), hashCode(), equals(), copy() 메서드를 생성해준다.
// 옆에 써준 게 일종의 생성자라고 볼 수 있다.
//data class Customer(var id: Long =0, var name: String = "")

/** using inner class */

// telephone에 대해서 null값이 가능하도록 처리 -> null값에 대해서는 직렬화하지 않도록 진행
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class Customer(var id: Long = 0L, var name: String = "", var telephone: Telephone? = null) {
    data class Telephone(var countryCode: String = "", var telephoneNumber: String = "")
}