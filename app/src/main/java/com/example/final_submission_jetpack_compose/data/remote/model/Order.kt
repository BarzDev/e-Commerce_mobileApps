package com.example.final_submission_jetpack_compose.data.remote.model

import java.util.Date

data class Order(
    val id: String,
    val date: Date,
    val total: Float,
    val items: List<Cart>
)
