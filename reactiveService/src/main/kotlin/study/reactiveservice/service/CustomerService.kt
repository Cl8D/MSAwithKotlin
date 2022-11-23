package study.reactiveservice.service

import study.reactiveservice.domain.Customer

interface CustomerService {
    fun getCustomer(id: Long) : Customer?
    fun searchCustomer(nameFilter: String) : List<Customer>
}