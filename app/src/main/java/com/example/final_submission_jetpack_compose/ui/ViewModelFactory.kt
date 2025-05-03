package com.example.final_submission_jetpack_compose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_submission_jetpack_compose.data.ProductRepository
import com.example.final_submission_jetpack_compose.ui.screen.cart.CartViewModel
import com.example.final_submission_jetpack_compose.ui.screen.history.HistoryViewModel
import com.example.final_submission_jetpack_compose.ui.screen.product.ProductViewModel
import com.example.final_submission_jetpack_compose.ui.screen.product_detail.ProductDetailViewModel

class ViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> ProductViewModel(repository) as T
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> ProductDetailViewModel(
                repository
            ) as T

            modelClass.isAssignableFrom(CartViewModel::class.java) -> CartViewModel(repository) as T
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> HistoryViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}