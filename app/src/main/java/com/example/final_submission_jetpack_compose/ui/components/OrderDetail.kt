package com.example.final_submission_jetpack_compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.final_submission_jetpack_compose.R
import com.example.final_submission_jetpack_compose.data.remote.model.Cart


@Composable
fun OrderDetail(
    date: String,
    time: String,
    total: String,
    item: List<Cart>,
    navigateBack: () -> Unit,

    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(48.dp)
                    .padding(5.dp)
                    .clickable { navigateBack() }
            )

            Text(
                text = "Detail Transaction",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = modifier
                    .weight(1f)
            )

            Spacer(modifier = Modifier.size(48.dp))
        }


        Column(modifier = modifier.padding(16.dp)) {
            Text(text = "Date: $date")
            Spacer(modifier.height(4.dp))

            Text(text = "Time: $time")
            Spacer(modifier.height(4.dp))

            Text(text = "Total: $$total")
            Spacer(modifier.height(4.dp))

            Text(text = "Detail item:")
            Spacer(modifier.height(4.dp))

            LazyColumn {
                items(item, key = { it.product.id }) { cart ->
                    ProductItem(
                        image = cart.product.image,
                        title = cart.product.title,
                        price = cart.product.price.toString(),
                        qty = cart.qty
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    image: String,
    title: String,
    price: String,
    qty: Int,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(image)
            .placeholder(R.drawable.ic_refresh_black)
            .error(R.drawable.ic_broken_image_black)
            .build()
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(90.dp)
                .background(Color.White)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = stringResource(R.string.price, price),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )
        }

        Text(
            text = "X$qty",
            modifier = Modifier
                .weight(1f),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

    }
}
