package com.example.final_submission_jetpack_compose.ui.screen.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_submission_jetpack_compose.di.Injection
import com.example.final_submission_jetpack_compose.ui.ViewModelFactory
import com.example.final_submission_jetpack_compose.ui.common.UiState
import com.example.final_submission_jetpack_compose.ui.components.ErrorHandlerComponent
import com.example.final_submission_jetpack_compose.ui.components.LoadingComponent
import com.example.final_submission_jetpack_compose.ui.components.OrderDetail
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HistoryDetailScreen(
    viewModel: HistoryViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository()),
    ),
    id: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.getDetailOrder(id)
    }

    when (val state = uiState) {
        is UiState.Loading -> {
            LoadingComponent()
        }

        is UiState.Success -> {
            val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            OrderDetail(
                date = dateFormat.format(state.data.date),
                time = timeFormat.format(state.data.date),
                total = state.data.total.toString(),
                item = state.data.items,
                navigateBack = navigateBack,
                modifier = modifier
            )
        }

        is UiState.Error -> {
            ErrorHandlerComponent(state.errorMessage) { viewModel.getDetailOrder(id) }
        }
    }
}