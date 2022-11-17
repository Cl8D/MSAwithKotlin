package study.basicService.common.exception

// 에러에 대한 응답을 저장하는 데이터 클래스.
data class ErrorResponse(val error: String, val message: String)
