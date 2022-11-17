package study.reactiveservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveServiceApplication

fun main(args: Array<String>) {
	runApplication<ReactiveServiceApplication>(*args)
}
