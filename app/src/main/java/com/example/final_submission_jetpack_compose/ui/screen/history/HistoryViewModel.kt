package com.example.final_submission_jetpack_compose.ui.screen.history

import androidx.lifecycle.ViewModel
import com.example.final_submission_jetpack_compose.data.ProductRepository
import com.example.final_submission_jetpack_compose.data.remote.model.Order
import com.example.final_submission_jetpack_compose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HistoryViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Order>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Order>> get() = _uiState

    val orders = repository.orders

    fun getDetailOrder(id: String) {
        _uiState.value = UiState.Loading

        try {
            val detail = repository.getDetailTransaction(id)
            _uiState.value = UiState.Success(detail)
        } catch (e: Exception) {
            val msg = "Failed to get detail transaction"
            _uiState.value = UiState.Error(msg)
        }
    }
}