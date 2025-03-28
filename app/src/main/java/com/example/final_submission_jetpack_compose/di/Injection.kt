package com.example.final_submission_jetpack_compose.di

import com.example.final_submission_jetpack_compose.data.ProductRepository

object Injection {
    fun provideRepository(): ProductRepository {
        return ProductRepository.getInstance()
    }
}