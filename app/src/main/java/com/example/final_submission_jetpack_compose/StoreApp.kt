package com.example.final_submission_jetpack_compose

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.final_submission_jetpack_compose.di.Injection
import com.example.final_submission_jetpack_compose.ui.ViewModelFactory
import com.example.final_submission_jetpack_compose.ui.navigation.NavigationItem
import com.example.final_submission_jetpack_compose.ui.navigation.Screen
import com.example.final_submission_jetpack_compose.ui.screen.about.AboutScreen
import com.example.final_submission_jetpack_compose.ui.screen.cart.CartScreen
import com.example.final_submission_jetpack_compose.ui.screen.history.HistoryDetailScreen
import com.example.final_submission_jetpack_compose.ui.screen.history.HistoryScreen
import com.example.final_submission_jetpack_compose.ui.screen.product.ProductScreen
import com.example.final_submission_jetpack_compose.ui.screen.product_detail.ProductDetailScreen
import com.example.final_submission_jetpack_compose.ui.screen.product_detail.ProductDetailViewModel

@Composable
fun StoreApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute in listOf(
                    Screen.Product.route,
                    Screen.Cart.route,
                    Screen.History.route
                )
            ) {
                TopBar(navController)
            }
        },
        bottomBar = {
            if (currentRoute in listOf(
                    Screen.Product.route,
                    Screen.Cart.route,
                    Screen.History.route
                )
            ) {
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
                route = Screen.History.route,
                enterTransition = { slideInHorizontally(animationSpec = tween(300)) },
                exitTransition = { slideOutHorizontally(animationSpec = tween(300)) }
            ) {
                HistoryScreen(
                    navigateToDetail = { orderId ->
                        navController.navigate(Screen.HistoryDetail.createRoute(orderId))
                    }
                )
            }

            composable(
                route = Screen.Product.route,
                enterTransition = {
                    if (currentRoute == Screen.Cart.route) {
                        slideInHorizontally(animationSpec = tween(300))
                    } else {
                        slideInHorizontally(
                            animationSpec = tween(300),
                            initialOffsetX = { it })
                    }
                },
                exitTransition = {
                    if (currentRoute == Screen.Cart.route) {
                        slideOutHorizontally(animationSpec = tween(300))
                    } else {
                        slideOutHorizontally(
                            animationSpec = tween(300),
                            targetOffsetX = { -it })
                    }
                }
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
                    navigateToCart = {
                        navController.popBackStack()
                        navController.navigate(Screen.Cart.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }

            composable(
                route = Screen.HistoryDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
            ) {
                HistoryDetailScreen(
                    id = it.arguments?.getString("id") ?: "",
                    navigateBack = { navController.navigateUp() },
                )
            }

        }
    }
}


@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),

    ) {
    val cartCount by viewModel.cartCount.collectAsStateWithLifecycle()

    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_history),
                icon = Icons.Default.HistoryEdu,
                screen = Screen.History
            ),
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
                    if (item.screen == Screen.Cart) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(
                                        text = cartCount.toString(),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                        )
                    }
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
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            Box(
                contentAlignment = Alignment.Center
            ) {
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
        }
    )
}


@Composable
@Preview(showBackground = true)
fun StoreAppPreview() {
    StoreApp()
}