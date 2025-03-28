package com.example.final_submission_jetpack_compose.ui.navigation

sealed class Screen(val route: String) {
    data object Product : Screen("product")
    data object Cart : Screen("cart")
    data object About : Screen("about")
    data object ProductDetail : Screen("product_detail/{id}") {
        fun createRoute(id: Int) = "product_detail/$id"
    }

}