package com.example.final_submission_jetpack_compose.ui.screen.cart

import androidx.lifecycle.ViewModel
import com.example.final_submission_jetpack_compose.data.ProductRepository
import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import com.example.final_submission_jetpack_compose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ProductItem>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ProductItem>> get() = _uiState

    val cart = repository.cart

    fun increaseQty(id: Int) {
        repository.updateCart(id, 1)
    }

    fun decreaseQty(id: Int) {
        repository.updateCart(id, -1)
    }

    fun removeItem(id: Int) {
        repository.removeItem(id)
    }

    fun addTransaction() {
        repository.addTransaction(cart.value)
    }
}