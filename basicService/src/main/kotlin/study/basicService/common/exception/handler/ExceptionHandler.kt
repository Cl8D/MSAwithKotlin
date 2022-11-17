package study.basicService.common.exception.handler

import com.fasterxml.jackson.core.JsonParseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import study.basicService.common.exception.CustomerNotFoundException
import study.basicService.common.exception.ErrorResponse
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionHandler {

    // 에러 처리. 이런 식으로 매개변수 받고 리턴값 받는 거 잘 확인해놔야겠다...
    // JsonParseException이 발생하면 @ControllerAdvice에서 해당 예외를 처리할 수 있는 핸들러를 검색하여 오류를 전송하는 형태이다.
    @ExceptionHandler(JsonParseException::class)
    fun jsonParseExceptionHandler(servletRequest: HttpServletRequest,
                                  exception:JsonParseException): ResponseEntity<ErrorResponse> {

        // 예외 메시지의 디폴트 값으로 invalid json을 설정해준다.
        return ResponseEntity(
                ErrorResponse("Json Parse Error!", exception.message ?:"invalid json"),
                HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CustomerNotFoundException::class)
    fun customerNotFoundExceptionHandler(servletRequest: HttpServletRequest,
                                         exception: CustomerNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
                // !!을 사용하는 이유는 null인 경우 NPE를 발생시키도록 하기 위해서이다.
                ErrorResponse("Customer Not Found Error!", exception.message!!),
                HttpStatus.BAD_REQUEST

        /*
            메시지는 이런 식으로 출력된다.
            {
              "error": "Customer Not Found Error!",
              "message": "customer '4' is not found"
            }
         */
        )
    }

}