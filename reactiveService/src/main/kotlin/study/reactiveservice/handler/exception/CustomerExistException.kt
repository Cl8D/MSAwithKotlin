package study.reactiveservice.handler.exception

data class CustomerExistException(override val message: String) : Exception(message)
