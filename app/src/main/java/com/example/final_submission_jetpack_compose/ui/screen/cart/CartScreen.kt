package com.example.final_submission_jetpack_compose.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_submission_jetpack_compose.di.Injection
import com.example.final_submission_jetpack_compose.ui.ViewModelFactory
import com.example.final_submission_jetpack_compose.ui.theme.SoftWhite


@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    modifier: Modifier = Modifier
) {
    val cartState by viewModel.cart.collectAsState(emptyList())

    Column(
        modifier = Modifier
            .background(SoftWhite)
    ) {
        LazyColumn {
            items(cartState, key = { it.product.id }) { cartItem ->
                Text(text = "${cartItem.product.title} - Qty: ${cartItem.qty}")
            }
        }
    }
//    Payment()
}


//@Composable
//fun CartList() {
//    Column() {
//        LazyColumn(
//            contentPadding = PaddingValues(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            modifier = Modifier.weight(weight = 1f)
//        ) {
//
//        }
//    }
//
//}

@Composable
fun Payment() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Total : $ 1455",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Button(onClick = {}) {
            Text(text = "Payment")
        }

    }
}

//@Composable
//@Preview(showBackground = true)
//fun CartScreenPreview() {
//    CartScreen()
//}
