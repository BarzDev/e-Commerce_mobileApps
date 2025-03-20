package com.example.final_submission_jetpack_compose.data.remote.model

data class ProductItem(
	val image: String,
	val price: Any,
	val rating: Rating,
	val description: String,
	val id: Int,
	val title: String,
	val category: String
)

data class Rating(
	val rate: Any,
	val count: Int
)



