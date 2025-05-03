package com.example.final_submission_jetpack_compose.data

import com.example.final_submission_jetpack_compose.data.remote.model.Cart
import com.example.final_submission_jetpack_compose.data.remote.model.Order
import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import com.example.final_submission_jetpack_compose.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class ProductRepository {
    private val _products = MutableStateFlow<List<ProductItem>>(emptyList())
    val products = _products.asStateFlow()

    private val _cart = MutableStateFlow<List<Cart>>(emptyList())
    val cart = _cart.asStateFlow()

    private val _order = MutableStateFlow<List<Order>>(emptyList())
    val orders = _order.asStateFlow()

    private val _cartCount = MutableStateFlow(0)
    val cartCount = _cartCount.asStateFlow()

    suspend fun fetchData() {
        val response = ApiConfig.getApiService().getProducts()
        _products.value = response
    }

    suspend fun getProduct(id: Int): ProductItem {
        return ApiConfig.getApiService().getProductById(id)
    }

    fun filterProducts(query: String): List<ProductItem> {
        return _products.value.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    private fun updateCartCount() {
        _cartCount.value = _cart.value.sumOf { it.qty }
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
        updateCartCount()
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
            updateCartCount()
        }
    }

    fun removeItem(id: Int) {
        val currentCart = _cart.value.toMutableList()
        val existingItemIndex = currentCart.indexOfFirst { it.product.id == id }

        if (existingItemIndex != -1) {
            currentCart.removeAt(existingItemIndex)
            _cart.value = currentCart
            updateCartCount()
        }
    }

    fun clearCart() {
        _cart.value = emptyList()
        updateCartCount()
    }

    fun getCartCount(): Int {
        return _cart.value.sumOf { it.qty }
    }

    fun addTransaction(cart: List<Cart>) {
        val currentOrder = _order.value.toMutableList()

        val total = cart.fold(0f) { acc, item ->
            acc + (item.product.price * item.qty)
        }

        val order = Order(
            id = System.currentTimeMillis().toString(),
            date = Date(System.currentTimeMillis()),
            items = cart,
            total = total
        )

        currentOrder.add(order)
        _order.value = currentOrder

        clearCart()
    }

    fun getDetailTransaction(id: String): Order {
        return _order.value.first { it.id == id }
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