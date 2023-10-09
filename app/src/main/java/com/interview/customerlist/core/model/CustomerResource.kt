package com.interview.customerlist.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CustomerResource(
    @Json(name = "id")
    val id: Int,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "imageUrl")
    val imageUrl: String?,
    @Json(name = "stickers")
    val stickers: List<String>
)

@JsonClass(generateAdapter = true)
data class CustomerListResource(
    @Json(name = "customers")
    val customers: List<CustomerResource>
)
