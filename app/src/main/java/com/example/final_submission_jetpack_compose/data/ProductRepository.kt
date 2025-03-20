package com.example.final_submission_jetpack_compose.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import com.example.final_submission_jetpack_compose.data.remote.retrofit.ApiConfig

class ProductRepository {
    val _products = MutableLiveData<List<ProductItem>>()
    val _error = MutableLiveData<String>()


    suspend fun fetchData(){
        try {
            val response = ApiConfig.getApiService().getProducts()
            _products.postValue(response)
        } catch (e: Exception) {
            _error.postValue("Terjadi kesalahan: ${e.message}")
            Log.e("ProductRepository", "Error: ${e.message}", e)
        }
    }
}