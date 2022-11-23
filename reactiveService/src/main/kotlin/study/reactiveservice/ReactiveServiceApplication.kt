package study.reactiveservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveServiceApplication

fun main(args: Array<String>) {
	/**
	 * Netty로 동작한다.
	 * index.html을 구성하였는데, 이 작업은 블록킹이 아닌 리액티브하게 처리되며,
	 * 서버가 페이지를 읽기 시작하면 데이터를 가져오는 즉시 정보를 보내게 된다.
	 * 이후, 파일에서 데이터를 읽고 클라이언트로 데이터를 다시 전송하게 되는데,
	 * 실제로 요청한 페이지의 데이터를 읽기, 보내기를 차단하지 않고 다른 클라이언트의 요청에게도 동일하게 응답이 가능하다.
	 * */
	runApplication<ReactiveServiceApplication>(*args)
}
