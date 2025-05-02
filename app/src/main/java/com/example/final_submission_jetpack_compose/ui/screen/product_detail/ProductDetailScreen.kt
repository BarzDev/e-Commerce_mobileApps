package com.example.final_submission_jetpack_compose.ui.screen.product_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_submission_jetpack_compose.di.Injection
import com.example.final_submission_jetpack_compose.ui.ViewModelFactory
import com.example.final_submission_jetpack_compose.ui.common.UiState
import com.example.final_submission_jetpack_compose.ui.components.ErrorHandlerComponent
import com.example.final_submission_jetpack_compose.ui.components.LoadingComponent
import com.example.final_submission_jetpack_compose.ui.components.ProductDetail

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    id: Int,
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cartCount by viewModel.cartCount.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.getProductById(id)
    }

    when (val state = uiState) {
        is UiState.Loading -> {
            LoadingComponent()
        }

        is UiState.Success -> {
            ProductDetail(
                title = state.data.title,
                image = state.data.image,
                price = state.data.price.toString(),
                description = state.data.description,
                rate = state.data.rating.rate.toString(),
                count = state.data.rating.count.toString(),
                navigateBack = navigateBack,
                navigateToCart = navigateToCart,
                cartCount = cartCount,
                addCart = { viewModel.addToCart(product = state.data, context = context) }
            )
        }

        is UiState.Error -> {
            ErrorHandlerComponent(state.errorMessage) { viewModel.getProductById(id) }
        }
    }
}


