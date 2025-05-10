package com.example.final_submission_jetpack_compose.ui.screen.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun AboutScreen(
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = { AppBar(navigateBack) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
//                    .background(SoftWhite),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                ItemProfile("UserID : Id-2457XS9")
                ItemProfile("Username : John Wick")
                ItemProfile("Email : jhon_wick@mail.com")
                ItemProfile("Phone : 0812 3456 7890")
                ItemProfile("Account : Private account")
            }
        }
    )
}

@Composable
fun ItemProfile(
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navigateBack: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text("Profile") },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(navigateBack = {})
}
