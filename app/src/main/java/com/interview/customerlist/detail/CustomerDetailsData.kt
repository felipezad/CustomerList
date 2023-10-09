package com.interview.customerlist.detail

data class AddressData(
    val street: String,
    val city: String,
    val state: String,
    val zip: String,
    val country: String
)

data class CustomerDetailsData(
    val id: Int,
    val imageUrl: String?,
    val currentLatitude: Double,
    val currentLongitude: Double,
    val firstName: String,
    val lastName: String,
    val stickers: List<String>,
    val gender: String,
    val phoneNumber: String,
    val address: AddressData
)
