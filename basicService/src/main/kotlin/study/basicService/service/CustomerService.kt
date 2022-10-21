package study.basicService.service

import study.basicService.model.Customer

/** Customer Service Layer */
interface CustomerService {
    fun getCustomer(id: Long) : Customer? // null-safety
    fun createCustomer(customer: Customer)
    fun deleteCustomer(id: Long)
    fun updateCustomer(id: Long, customer: Customer)
    fun searchCustomers(nameFilter: String) : List<Customer>
}