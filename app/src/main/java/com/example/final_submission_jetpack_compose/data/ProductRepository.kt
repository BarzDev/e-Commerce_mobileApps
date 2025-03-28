package com.example.final_submission_jetpack_compose.data

import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import com.example.final_submission_jetpack_compose.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductRepository {
    private val _products = MutableStateFlow<List<ProductItem>>(emptyList())
    val products = _products.asStateFlow()


    suspend fun fetchData() {
        val response = ApiConfig.getApiService().getProducts()
        _products.value = response
//            Log.i("ProductRepository", "Response: $response")
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