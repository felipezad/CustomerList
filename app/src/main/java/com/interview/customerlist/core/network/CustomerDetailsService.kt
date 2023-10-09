package com.interview.customerlist.core.network

import com.interview.customerlist.core.model.CustomerDetailsResource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CustomerDetailsService {

    @GET("users/{id}")
    suspend fun getCustomerDetails(@Path("id") id: Int): Response<CustomerDetailsResource>
}
