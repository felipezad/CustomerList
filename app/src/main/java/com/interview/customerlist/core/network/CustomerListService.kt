package com.interview.customerlist.core.network

import com.interview.customerlist.core.model.CustomerListResource
import retrofit2.Response
import retrofit2.http.GET

interface CustomerListService {
    @GET("users")
    suspend fun getCustomerList(): Response<CustomerListResource>
}
