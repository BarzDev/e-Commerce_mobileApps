package com.example.final_submission_jetpack_compose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RatingComponent(
    rate: String,
    count: String,
    iconSize: Int,
    gap: Int,
    fontWeight: FontWeight,
    textStyle: TextStyle

) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(gap.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                tint = Color.Yellow,
                modifier = Modifier.size(iconSize.dp)
            )
            Text(
                text = rate,
                fontWeight = fontWeight,
                style = textStyle
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Selling",
                tint = Color.Red,
                modifier = Modifier.size(iconSize.dp)
            )
            Text(
                text = count,
                fontWeight = fontWeight,
                style = textStyle
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun RatingComponentPreview() {
    RatingComponent(
        gap = 16,
        iconSize = 20,
        rate = "4.5F", count = "100",
        fontWeight = FontWeight.Bold,
        textStyle = MaterialTheme.typography.bodyMedium
    )
}