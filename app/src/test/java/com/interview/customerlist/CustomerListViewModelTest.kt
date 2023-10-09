package com.interview.customerlist

import com.interview.customerlist.services.FakeBrokenCostumerServiceList
import com.interview.customerlist.services.FakeCostumerServiceList
import com.interview.customerlist.customers.CustomerListUiState
import com.interview.customerlist.customers.CustomerListViewModel
import com.interview.customerlist.customers.CustomerRepository
import com.interview.customerlist.core.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CustomerListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var customerListViewModel: CustomerListViewModel? = null

    @After
    fun destroy() {
        customerListViewModel = null
    }

    @Test
    fun `When Customer are Retrieved the ViewModel State should be Success`() = runTest {

        val customerRepository = CustomerRepository(
            customerListService = FakeCostumerServiceList(),
            ioDispatcher = mainDispatcherRule.testDispatcher
        )
        customerListViewModel = CustomerListViewModel(
            customerRepository
        )
        val state = customerListViewModel?.customerListUiState

        assert(customerListViewModel?.customerListUiState?.value is CustomerListUiState.Initial)

        assertTrue(state?.value is CustomerListUiState.Initial)
        customerListViewModel?.getListCustomers()

        customerListViewModel?.customerListUiState
        assertTrue(state?.value is CustomerListUiState.Success)
    }

    @Test
    fun `When Customer are NOT Retrieved the ViewModel State should be Failure`() = runTest {

        val customerRepository = CustomerRepository(
            ioDispatcher = mainDispatcherRule.testDispatcher,
            customerListService = FakeBrokenCostumerServiceList()
        )
        customerListViewModel = CustomerListViewModel(
            customerRepository
        )
        val state = customerListViewModel?.customerListUiState

        assert(customerListViewModel?.customerListUiState?.value is CustomerListUiState.Initial)

        assertTrue(state?.value is CustomerListUiState.Initial)
        customerListViewModel?.getListCustomers()

        customerListViewModel?.customerListUiState
        assertTrue(state?.value is CustomerListUiState.LoadFailed)
    }
}