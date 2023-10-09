package com.interview.customerlist.detail

import android.util.Log
import com.interview.customerlist.core.model.AddressResource
import com.interview.customerlist.core.model.CustomerDetailsResource
import com.interview.customerlist.core.network.CustomerDetailsService
import com.interview.customerlist.core.network.CustomerDispatchers
import com.interview.customerlist.core.network.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerDetailsRepository @Inject constructor(
    private val customerDetailsService: CustomerDetailsService,
    @Dispatcher(CustomerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun getCustomer(customerID: Int): CustomerDetailsData? {
        return withContext(ioDispatcher) {
            val response = customerDetailsService.getCustomerDetails(customerID)
            if (response.isSuccessful) {
                mapToCustomerDetail(response.body())
            } else {
                throw CustomerDetailDataFetchException("Error fetching customer data")
            }
        }
    }

    private fun mapToCustomerDetail(customerDetailResource: CustomerDetailsResource?): CustomerDetailsData? {
        val customers = customerDetailResource?.mapToCustomerData()
        return customers
    }

    private fun CustomerDetailsResource.mapToCustomerData(): CustomerDetailsData {
        return CustomerDetailsData(
            id = this.id,
            imageUrl = this.imageUrl,
            currentLatitude = this.currentLatitude,
            currentLongitude = this.currentLongitude,
            firstName = this.firstName,
            lastName = this.lastName,
            stickers = this.stickers,
            gender = this.gender,
            phoneNumber = this.phoneNumber,
            address = this.address.toAddress()
        )
    }

    private fun AddressResource.toAddress(): AddressData {
        return AddressData(
            street = this.street,
            city = this.city,
            state = this.state,
            zip = this.zip,
            country = this.country
        )
    }
}
