package com.interview.customerlist.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerDetailViewModel @Inject constructor(
    private val customerDetailsRepository: CustomerDetailsRepository
) : ViewModel() {

    private var _customerListUiState: MutableStateFlow<CustomerDetailUiState> =
        MutableStateFlow(CustomerDetailUiState.Initial)

    val customerListUiState: StateFlow<CustomerDetailUiState> = _customerListUiState

    fun getCustomerDetail(customerID: Int?) {
        if (customerID != null) {
            viewModelScope.launch {
                try {
                    _customerListUiState.value = CustomerDetailUiState.Loading
                    updateUIState(customerDetailsRepository.getCustomer(customerID))
                } catch (e: Exception) {
                    _customerListUiState.value = CustomerDetailUiState.LoadFailed
                }
            }
        } else {
            _customerListUiState.value = CustomerDetailUiState.LoadFailed
        }
    }

    private fun updateUIState(data: CustomerDetailsData?) {
        when {
            data != null -> {
                _customerListUiState.value =
                    CustomerDetailUiState.Success(data)
            }

            else -> {
                _customerListUiState.value = CustomerDetailUiState.LoadFailed
            }
        }
    }
}