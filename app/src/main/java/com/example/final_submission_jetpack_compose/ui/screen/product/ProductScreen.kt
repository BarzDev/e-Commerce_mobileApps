package com.example.final_submission_jetpack_compose.ui.screen.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_submission_jetpack_compose.data.ProductRepository
import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import com.example.final_submission_jetpack_compose.ui.common.UiState
import com.example.final_submission_jetpack_compose.ui.components.ErrorScreen
import com.example.final_submission_jetpack_compose.ui.components.Loading
import com.example.final_submission_jetpack_compose.ui.components.ProductCard

@Composable
fun ProductScreen(
    viewModel: ProductViewModel = viewModel(factory = ProductViewModel.Factory(ProductRepository())),
    navigateToDetail: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState(initial = UiState.Loading)
    val products by viewModel.products.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    when (uiState) {
        is UiState.Loading -> {
            Loading()
        }

        is UiState.Success -> {
            ProductList(
                products,
                navigateToDetail = navigateToDetail
            )
        }

        is UiState.Error -> {
            val errorMessage = (uiState as UiState.Error).errorMessage
            ErrorScreen(errorMessage) { viewModel.fetchProducts() }
        }
    }
}

@Composable
fun ProductList(
    products: List<ProductItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(products) { product ->
            ProductCard(
                product,
                modifier = Modifier.clickable {
                    navigateToDetail(product.id)
                })
        }
    }
}

