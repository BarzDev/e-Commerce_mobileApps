package com.example.final_submission_jetpack_compose.data

import com.example.final_submission_jetpack_compose.data.remote.model.Cart
import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import com.example.final_submission_jetpack_compose.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

class ProductRepository {
    private val _products = MutableStateFlow<List<ProductItem>>(emptyList())
    val products = _products.asStateFlow()

    private val _cart = MutableStateFlow<List<Cart>>(emptyList())
    val cart = _cart.asStateFlow()

    suspend fun fetchData() {
        val response = ApiConfig.getApiService().getProducts()
        _products.value = response
    }

    suspend fun getProduct(id: Int): ProductItem {
        return ApiConfig.getApiService().getProductById(id)
    }

    fun addToCart(product: ProductItem) {
        val currentCart = _cart.value.toMutableList()
        val existingItemIndex = currentCart.indexOfFirst { it.product.id == product.id }

        if (existingItemIndex != -1) {
            val existingItem = currentCart[existingItemIndex]
            currentCart[existingItemIndex] = existingItem.copy(qty = existingItem.qty + 1)
        } else {
            currentCart.add(Cart(product, 1))
        }

        _cart.value = currentCart
    }

    fun updateCart(id: Int, count: Int) {
        val currentCart = _cart.value.toMutableList()
        val existingItemIndex = currentCart.indexOfFirst { it.product.id == id }

        if (existingItemIndex != -1) {
            val existingItem = currentCart[existingItemIndex]
            val newQty = existingItem.qty + count

            if (newQty > 0) {
                currentCart[existingItemIndex] = existingItem.copy(qty = newQty)
            } else {
                currentCart.removeAt(existingItemIndex)
            }
            _cart.value = currentCart
        }
    }

    fun removeItem(id: Int) {
        val currentCart = _cart.value.toMutableList()
        val existingItemIndex = currentCart.indexOfFirst { it.product.id == id }

        if (existingItemIndex != -1) {
            currentCart.removeAt(existingItemIndex)
            _cart.value = currentCart
        }
    }



    fun clearCart() {
        _cart.value = emptyList()
    }


    companion object {
        @Volatile
        private var instance: ProductRepository? = null

        fun getInstance(): ProductRepository =
            instance ?: synchronized(this) {
                ProductRepository().apply {
                    instance = this
                }
            }
    }
}