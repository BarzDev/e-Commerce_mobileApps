package com.example.final_submission_jetpack_compose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_submission_jetpack_compose.data.ProductRepository
import com.example.final_submission_jetpack_compose.ui.screen.cart.CartViewModel
import com.example.final_submission_jetpack_compose.ui.screen.product.ProductViewModel
import com.example.final_submission_jetpack_compose.ui.screen.product_detail.ProductDetailViewModel

class ViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}