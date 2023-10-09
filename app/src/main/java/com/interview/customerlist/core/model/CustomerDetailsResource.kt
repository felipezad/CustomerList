package com.interview.customerlist.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressResource(
    @Json(name = "street")
    val street: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "state")
    val state: String,
    @Json(name = "zip")
    val zip: String,
    @Json(name = "country")
    val country: String
)

@JsonClass(generateAdapter = true)
data class CustomerDetailsResource(
    val id: Int,
    @Json(name = "imageUrl")
    val imageUrl: String?,
    @Json(name = "currentLatitude")
    val currentLatitude: Double,
    @Json(name = "currentLongitude")
    val currentLongitude: Double,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "stickers")
    val stickers: List<String>,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "address")
    val address: AddressResource
)
