package com.example.final_submission_jetpack_compose.data.remote.retrofit

import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import retrofit2.http.GET


interface ApiService {
   @GET("products")
   suspend fun getProducts(): List<ProductItem>

}