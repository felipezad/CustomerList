package com.interview.customerlist.detail

sealed interface CustomerDetailUiState {
    data object Initial : CustomerDetailUiState
    data object Loading : CustomerDetailUiState
    data object LoadFailed : CustomerDetailUiState
    data class Success(
        val customer: CustomerDetailsData? = null,
    ) : CustomerDetailUiState
}