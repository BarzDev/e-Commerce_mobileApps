package com.example.final_submission_jetpack_compose.ui.screen.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    val filteredProducts = when (val state = uiState) {
        is UiState.Loading -> {
            LoadingComponent()
            emptyList<ProductItem>() // Loading state, return empty list
        }

        is UiState.Success -> {
            if (state.data.isEmpty()) {
                EmptyComponent(msg = stringResource(R.string.empty_product_msg))
                emptyList<ProductItem>() // No products found
            } else {
                // Filter produk berdasarkan query
                state.data.filter {
                    it.title.contains(query, ignoreCase = true)
                }
            }
        }

        is UiState.Error -> {
            ErrorHandlerComponent(state.errorMessage) { viewModel.fetchProducts() }
            emptyList<ProductItem>() // Error state, return empty list
        }
    }

    // Tampilan UI dengan search field
    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        if (filteredProducts.isEmpty()) {
            EmptyComponent(msg = stringResource(R.string.empty_product_msg))
        } else {
            ProductList(
                products = filteredProducts,
                navigateToDetail = navigateToDetail
            )
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

