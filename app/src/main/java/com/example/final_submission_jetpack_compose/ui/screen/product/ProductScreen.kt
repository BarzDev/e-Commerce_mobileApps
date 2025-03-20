package com.example.final_submission_jetpack_compose.ui.screen.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.final_submission_jetpack_compose.R
import com.example.final_submission_jetpack_compose.data.ProductRepository
import com.example.final_submission_jetpack_compose.ui.theme.Final_Submission_Jetpack_ComposeTheme


@Composable
fun ProductScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(stringResource(R.string.menu_product))
    }
}


//@Composable
//fun ProductScreen(viewModel: ProductViewModel) {
//    val products by viewModel.products.observeAsState(emptyList()) // 🔹 Gunakan observeAsState
//    val error by viewModel.error.observeAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchProducts()
//    }
//
//    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//        Column(modifier = Modifier.padding(innerPadding)) {
//            if (error != null) {
//                Text("Error: ${error!!}", color = MaterialTheme.colorScheme.error)
//            } else if (products.isEmpty()) {
//                Text("Memuat data...")
//            } else {
//                products.forEach { product ->
//                    Text(text = product.title) // 🔹 Produk akan tampil tanpa error
//                }
//            }
//        }
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    Final_Submission_Jetpack_ComposeTheme {
//        ProductScreen(viewModel = ProductViewModel(ProductRepository()))
//    }
//}