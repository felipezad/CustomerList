package com.interview.customerlist.customers

sealed interface CustomerListUiState {
    data object Initial : CustomerListUiState
    data object Loading : CustomerListUiState
    data object LoadFailed : CustomerListUiState
    data class Success(
        val customers: List<Customer> = emptyList(),
    ) : CustomerListUiState
}