package com.interview.customerlist.services

import com.interview.customerlist.core.model.CustomerListResource
import com.interview.customerlist.core.network.CustomerListService
import retrofit2.Response

class FakeBrokenCostumerServiceList : CustomerListService {

    override suspend fun getCustomerList(): Response<CustomerListResource> {
        throw Exception("FakeBrokenCostumerServiceList")
    }
}