package com.example.final_submission_jetpack_compose.ui.screen.cart

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_submission_jetpack_compose.R
import com.example.final_submission_jetpack_compose.data.remote.model.Cart
import com.example.final_submission_jetpack_compose.di.Injection
import com.example.final_submission_jetpack_compose.ui.ViewModelFactory
import com.example.final_submission_jetpack_compose.ui.components.CartItem
import com.example.final_submission_jetpack_compose.ui.components.EmptyComponent


@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val carts by viewModel.cart.collectAsStateWithLifecycle(emptyList())
    val totalPrice = carts.sumOf { it.product.price.toDouble() * it.qty }

    if (carts.isEmpty()) {
        EmptyComponent(
            msg = stringResource(R.string.empty_cart_msg),
        )
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
//                .background(SoftWhite)
        ) {
            CartList(
                carts = carts,
                viewModel = viewModel,
                modifier = Modifier.weight(1f)
            )

            Payment(
                totalPrice = totalPrice,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                clearCart = { viewModel.addTransaction() }

            )
            HorizontalDivider()
        }
    }
}


@Composable
fun CartList(
    carts: List<Cart>,
    viewModel: CartViewModel,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        items(carts, key = { it.product.id }) { cartItem ->
            CartItem(
                image = cartItem.product.image,
                title = cartItem.product.title,
                price = cartItem.product.price.toString(),
                count = cartItem.qty,
                onIncrease = { viewModel.increaseQty(cartItem.product.id) },
                onDecrease = { viewModel.decreaseQty(cartItem.product.id) },
                onDelete = { viewModel.removeItem(cartItem.product.id) }
            )
            HorizontalDivider()
        }
    }
}


@Composable
fun Payment(
    modifier: Modifier = Modifier,
    totalPrice: Double,
    clearCart: () -> Unit
) {
    val context = LocalContext.current
    val msg = stringResource(R.string.checkout_total_msg, totalPrice)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .padding(5.dp)
    ) {
        Text(
            text = stringResource(R.string.totalPrice, totalPrice),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Button(onClick = {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            clearCart()
        }) {
            Text(text = stringResource(R.string.checkout_total))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CartScreenPreview() {
    CartScreen()
}

@Composable
@Preview(showBackground = true)
fun PaymentPreview() {
    Payment(
        totalPrice = 100.00,
        clearCart = {}
    )
}

