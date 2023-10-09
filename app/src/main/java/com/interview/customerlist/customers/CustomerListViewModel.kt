package com.interview.customerlist.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerListViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private var _customerListUiState: MutableStateFlow<CustomerListUiState> =
        MutableStateFlow(CustomerListUiState.Initial)

    val customerListUiState: StateFlow<CustomerListUiState> = _customerListUiState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _customerListUiState.value = CustomerListUiState.LoadFailed
    }

    fun getListCustomers() {
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                _customerListUiState.value = CustomerListUiState.Loading
                updateUIState(customerRepository.getCustomers())
            } catch (e: Exception) {
                _customerListUiState.value = CustomerListUiState.LoadFailed
            }
        }
    }

    private fun updateUIState(data: CustomerList) {
        when {
            data.customers.isNotEmpty() -> {
                _customerListUiState.value =
                    CustomerListUiState.Success(data.customers)
            }

            else -> {
                _customerListUiState.value = CustomerListUiState.LoadFailed
            }
        }
    }

}