package com.example.final_submission_jetpack_compose

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.final_submission_jetpack_compose.ui.navigation.NavigationItem
import com.example.final_submission_jetpack_compose.ui.navigation.Screen
import com.example.final_submission_jetpack_compose.ui.screen.about.AboutActivity

@Composable
fun StoreApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomBar()
        },
        modifier = modifier
    ) { innerPadding ->
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_product),
                icon = Icons.Default.LocalMall,
                screen = Screen.Product
            ),
            NavigationItem(
                title = stringResource(R.string.menu_cart),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = false,
                onClick = {
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            Box(
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { val intent = Intent(context, AboutActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    )
}


@Composable
@Preview(showBackground = true)
fun StoreAppPreview() {
    StoreApp()
}