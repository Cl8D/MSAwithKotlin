package study.basicService.serialize

class SimpleObject {
    public val name = "hello"
    private val place = "world"
    /** private으로 선언된 값을 가져올 때 get으로 열어둔다. */
    // ObjectMapper가 내부적으로 직렬화 시 getXXX 메서드의 XXX를 따와서 JSON 객체에 직렬화시킨다.
    public fun getPlace() = place
}