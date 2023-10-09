package com.interview.customerlist.services

import com.interview.customerlist.core.model.CustomerListResource
import com.interview.customerlist.core.model.CustomerResource
import com.interview.customerlist.core.network.CustomerListService
import retrofit2.Response

class FakeCostumerServiceList : CustomerListService {

    val fakeCustomerResources = listOf(
        CustomerResource(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            gender = "MALE",
            phoneNumber = "123-456-7890",
            imageUrl = "https://fastly.picsum.photos/id/473/200/300.jpg?hmac=WYG6etF60iOJeGoFVY1hVDMakbBRS32ZDGNkVZhF6-8",
            stickers = listOf("Fam")
        ),
        CustomerResource(
            id = 2,
            firstName = "Jane",
            lastName = "Doe",
            gender = "FEMALE",
            phoneNumber = "123-456-7891",
            imageUrl = "https://fastly.picsum.photos/id/445/400/400.jpg?hmac=CCjqlZXQQ_5kl0X6naMjQKUWSbQloDYImyB9zGFOA8M",
            stickers = listOf("Fam", "Ban")
        ),
        CustomerResource(
            id = 3,
            firstName = "Bob",
            lastName = "Smith",
            gender = "MALE",
            phoneNumber = "123-456-7892",
            imageUrl = null,
            stickers = listOf("Ban")
        )
    )

    override suspend fun getCustomerList(): Response<CustomerListResource> {
        return Response.success(CustomerListResource(fakeCustomerResources))
    }
}