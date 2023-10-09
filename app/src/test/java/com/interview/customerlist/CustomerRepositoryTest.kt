package com.interview.customerlist

import com.interview.customerlist.core.MainDispatcherRule
import com.interview.customerlist.customers.CustomerDataFetchException
import com.interview.customerlist.customers.CustomerRepository
import com.interview.customerlist.services.FakeBrokenCostumerServiceList
import com.interview.customerlist.services.FakeCostumerServiceList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CustomerRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var customerListViewModel: CustomerRepository? = null

    @After
    fun destroy() {
        customerListViewModel = null
    }

    @Test
    fun `When Customer are Retrieved the repository should contain 3 Customers`() = runTest {

        val customerRepository = CustomerRepository(
            customerListService = FakeCostumerServiceList(),
            ioDispatcher = mainDispatcherRule.testDispatcher
        )

        val test = customerRepository.getCustomers()

        assertTrue(test.customers.isNotEmpty())
        assertTrue(test.customers.size == 3)
    }

    @Test(expected = CustomerDataFetchException::class)
    fun `When Customer CAN'T be retrieved the repository should trigger an Exception`() = runTest {

        val customerRepository = CustomerRepository(
            customerListService = FakeBrokenCostumerServiceList(),
            ioDispatcher = mainDispatcherRule.testDispatcher
        )

        customerRepository.getCustomers()
    }
}