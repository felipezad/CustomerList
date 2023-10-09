package com.interview.customerlist.customers

import com.interview.customerlist.core.model.CustomerListResource
import com.interview.customerlist.core.model.CustomerResource
import com.interview.customerlist.core.network.CustomerDispatchers
import com.interview.customerlist.core.network.CustomerListService
import com.interview.customerlist.core.network.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val customerListService: CustomerListService,
    @Dispatcher(CustomerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun getCustomers(): CustomerList {
        return withContext(ioDispatcher) {
            try {
                val response = customerListService.getCustomerList()
                if (response.isSuccessful) {
                    mapToCustomerList(response.body())
                } else {
                    throw CustomerDataFetchException("Error fetching customer data")
                }
            } catch (e: Exception) {
                throw CustomerDataFetchException(e.toString())
            }

        }
    }

    private fun mapToCustomerList(customerListResource: CustomerListResource?): CustomerList {
        val customers = customerListResource?.customers?.map { mapToCustomer(it) } ?: emptyList()
        return CustomerList(customers)
    }

    private fun mapToCustomer(customerResource: CustomerResource): Customer {
        return Customer(
            id = customerResource.id,
            firstName = customerResource.firstName,
            lastName = customerResource.lastName,
            gender = customerResource.gender,
            phoneNumber = customerResource.phoneNumber,
            imageUrl = customerResource.imageUrl,
            stickers = customerResource.stickers
        )
    }
}
