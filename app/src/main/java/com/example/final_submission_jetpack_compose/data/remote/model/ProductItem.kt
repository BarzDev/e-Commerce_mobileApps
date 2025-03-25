package com.example.final_submission_jetpack_compose.data.remote.model

data class ProductItem(
	val id: Int,
	val title: String,
	val price: Float,
	val description: String,
	val category: String,
	val image: String,
	val rating: Rating
)

data class Rating(
	val rate: Float,
	val count: Int
)



