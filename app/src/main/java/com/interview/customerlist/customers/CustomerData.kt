package com.interview.customerlist.customers

data class Customer(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val phoneNumber: String,
    val imageUrl: String?,
    val stickers: List<String>
)

data class CustomerList(
    val customers: List<Customer>
)
