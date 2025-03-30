package com.example.final_submission_jetpack_compose.ui.screen.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.final_submission_jetpack_compose.data.ProductRepository
import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import com.example.final_submission_jetpack_compose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    val products: StateFlow<List<ProductItem>> = repository.products

    private val _uiState: MutableStateFlow<UiState<List<ProductItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<ProductItem>>> get() = _uiState

    fun fetchProducts() {
        if (_uiState.value is UiState.Success) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                repository.fetchData()
                _uiState.value = UiState.Success(products.value)
            } catch (e: Exception) {
                val msg = "Failed to get product"
                _uiState.value = UiState.Error(msg)
            }
        }
    }

}