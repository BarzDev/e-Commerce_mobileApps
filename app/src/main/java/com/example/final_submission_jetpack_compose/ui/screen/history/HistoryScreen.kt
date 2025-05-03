package com.example.final_submission_jetpack_compose.ui.screen.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_submission_jetpack_compose.R
import com.example.final_submission_jetpack_compose.data.remote.model.Order
import com.example.final_submission_jetpack_compose.di.Injection
import com.example.final_submission_jetpack_compose.ui.ViewModelFactory
import com.example.final_submission_jetpack_compose.ui.components.EmptyComponent
import com.example.final_submission_jetpack_compose.ui.components.OrderItem

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository()),
    ),
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val orders by viewModel.orders.collectAsStateWithLifecycle(emptyList())

    if (orders.isEmpty()) {
        EmptyComponent(
            msg = stringResource(R.string.empty_order_msg),
        )
    } else {
        Column(modifier = modifier.padding(8.dp)) {
            Text(text = "Transaction History")
            Spacer(modifier.height(8.dp))
            HistoryList(orders, navigateToDetail)
        }
    }
}

@Composable
fun HistoryList(
    orders: List<Order>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = orders, key = { it.id }) { order ->
            OrderItem(
                date = order.date,
                total = order.total.toString(),
                navigateToDetail = { navigateToDetail(order.id) },
            )
            HorizontalDivider()
        }
    }
}