package com.example.final_submission_jetpack_compose.data.remote.model

data class ProductItem(
	val id: Int,
	val title: String,
	val price: Double,
	val description: String,
	val category: String,
	val image: String,
	val rating: Rating
)

data class Rating(
	val rate: Double,
	val count: Int
)



