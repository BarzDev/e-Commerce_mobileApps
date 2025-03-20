package com.example.final_submission_jetpack_compose.ui.screen.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.final_submission_jetpack_compose.data.ProductRepository
import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    val products: LiveData<List<ProductItem>> = repository._products
    val error: LiveData<String> = repository._error

    fun fetchProducts() {
        viewModelScope.launch {
            repository.fetchData()
        }
    }

    // ✅ ViewModel Factory di dalam ViewModel
    class Factory(private val repository: ProductRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}