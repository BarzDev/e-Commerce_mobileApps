package com.example.final_submission_jetpack_compose.ui.screen.product_detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_submission_jetpack_compose.R
import com.example.final_submission_jetpack_compose.data.ProductRepository
import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import com.example.final_submission_jetpack_compose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ProductItem>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ProductItem>> get() = _uiState

    val cart = repository.cart

    fun getProductById(id: Int) {
        if (_uiState.value is UiState.Success) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val product = repository.getProduct(id)
                _uiState.value = UiState.Success(product)
            } catch (e: Exception) {
                val msg = "Failed to get detail product"
                _uiState.value = UiState.Error(msg)
            }
        }
    }

    fun addToCart(product: ProductItem, context: Context) = viewModelScope.launch {
        repository.addToCart(product)
        Toast.makeText(context, R.string.added_cart_msg, Toast.LENGTH_SHORT).show()
    }
}

