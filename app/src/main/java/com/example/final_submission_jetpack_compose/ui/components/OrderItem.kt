package com.example.final_submission_jetpack_compose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderItem(
    date: Date,
    total: String,
    navigateToDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("dd MM yyyy, HH:mm", Locale.getDefault())

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = "Date  : ${dateFormat.format(date)}",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = "Total : $$total",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )
        }
        Button(onClick = { navigateToDetail() }) {
            Text(text = "detail")
        }
    }
}
