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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_submission_jetpack_compose.R
import com.example.final_submission_jetpack_compose.data.remote.model.ProductItem
import com.example.final_submission_jetpack_compose.di.Injection
import com.example.final_submission_jetpack_compose.ui.ViewModelFactory
import com.example.final_submission_jetpack_compose.ui.common.UiState
import com.example.final_submission_jetpack_compose.ui.components.EmptyComponent
import com.example.final_submission_jetpack_compose.ui.components.ErrorHandlerComponent
import com.example.final_submission_jetpack_compose.ui.components.LoadingComponent
import com.example.final_submission_jetpack_compose.ui.components.ProductCard

@Composable
fun ProductScreen(
    viewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState(initial = UiState.Loading)

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    when (val state = uiState) {
        is UiState.Loading -> {
            LoadingComponent()
        }

        is UiState.Success -> {
            if (state.data.isEmpty()) {
                EmptyComponent(
                    msg = stringResource(R.string.empty_product_msg)
                )
            } else {
                ProductList(
                    products = state.data,
                    navigateToDetail = navigateToDetail
                )
            }
        }

        is UiState.Error -> {
            ErrorHandlerComponent(state.errorMessage) { viewModel.fetchProducts() }
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
                title = product.title,
                image = product.image,
                price = product.price.toString(),
                count = product.rating.count.toString(),
                rate = product.rating.rate.toString(),
                modifier = Modifier.clickable {
                    navigateToDetail(product.id)
                },
            )
        }
    }
}

