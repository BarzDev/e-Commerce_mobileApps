package com.example.final_submission_jetpack_compose

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.final_submission_jetpack_compose.ui.components.SearchBar
import com.example.final_submission_jetpack_compose.ui.navigation.NavigationItem
import com.example.final_submission_jetpack_compose.ui.navigation.Screen
import com.example.final_submission_jetpack_compose.ui.screen.about.AboutScreen
import com.example.final_submission_jetpack_compose.ui.screen.cart.CartScreen
import com.example.final_submission_jetpack_compose.ui.screen.product.ProductScreen
import com.example.final_submission_jetpack_compose.ui.screen.product_detail.ProductDetailScreen

@Composable
fun StoreApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute !in listOf(Screen.About.route, Screen.ProductDetail.route)) {
                TopBar(navController, showSearchBar = currentRoute == Screen.Product.route)
            }
        },
        bottomBar = {
            if (currentRoute !in listOf(Screen.About.route, Screen.ProductDetail.route)) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Product.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.Product.route,
                enterTransition = { slideInHorizontally(animationSpec = tween(300)) },
                exitTransition = { slideOutHorizontally(animationSpec = tween(300)) }
            ) {
                ProductScreen(
                    navigateToDetail = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }

            composable(
                route = Screen.Cart.route,
                enterTransition = {
                    slideInHorizontally(
                        animationSpec = tween(300),
                        initialOffsetX = { it })
                },
                exitTransition = {
                    slideOutHorizontally(
                        animationSpec = tween(300),
                        targetOffsetX = { -it })
                }
            ) {
                CartScreen()
            }

            composable(
                route = Screen.About.route,
                enterTransition = {
                    slideInHorizontally(
                        animationSpec = tween(300),
                        initialOffsetX = { it })
                },
                exitTransition = {
                    slideOutHorizontally(
                        animationSpec = tween(100),
                        targetOffsetX = { -it })
                }
            ) {
                AboutScreen(
                    navigateBack = { navController.navigateUp() },
                )
            }

            composable(
                route = Screen.ProductDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
                enterTransition = {
                    slideInHorizontally(
                        animationSpec = tween(300),
                        initialOffsetX = { it })
                },
                exitTransition = {
                    slideOutHorizontally(
                        animationSpec = tween(100),
                        targetOffsetX = { -it })
                }
            ) {
                ProductDetailScreen(
                    id = it.arguments?.getInt("id") ?: 0,
                    navigateBack = { navController.navigateUp() },
                )
            }
        }
    }
}


@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

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
                        contentDescription = item.title,
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    showSearchBar: Boolean = false,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            if (isSearching && showSearchBar) {
                SearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleMedium
                )
            }

        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            if (showSearchBar) {
                IconButton(onClick = { isSearching = !isSearching }) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }
            IconButton(
                onClick = {
                    navController.navigate(Screen.About.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}


@Composable
@Preview(showBackground = true)
fun StoreAppPreview() {
    StoreApp()
}