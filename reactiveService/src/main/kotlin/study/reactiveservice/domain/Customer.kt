package study.reactiveservice.domain

data class Customer(var id:Long=1L, val name: String="", val telephone: Telephone? = null) {
    data class Telephone(var countryCode: String="", var telephoneNumber: String="")
}