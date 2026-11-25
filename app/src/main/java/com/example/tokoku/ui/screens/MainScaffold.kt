package com.example.tokoku.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.tokoku.viewmodel.MarketplaceViewModel
import com.example.tokoku.viewmodel.Screen

@Composable
fun MainScaffold(viewModel: MarketplaceViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedProduct by viewModel.selectedProduct.collectAsState()
    val orders by viewModel.orders.collectAsState()
    val selectedPaymentMethod by viewModel.selectedPaymentMethod.collectAsState()

    val cartItemCount = cartItems.sumOf { it.quantity }

    val showBottomBar = currentScreen is Screen.Home ||
            currentScreen is Screen.Explore ||
            currentScreen is Screen.Cart ||
            currentScreen is Screen.Profile

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentScreen is Screen.Home,
                        onClick = { viewModel.navigateTo(Screen.Home) },
                        icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Beranda") },
                        label = { Text("Beranda") }
                    )
                    NavigationBarItem(
                        selected = currentScreen is Screen.Explore,
                        onClick = { viewModel.navigateTo(Screen.Explore) },
                        icon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Jelajahi") },
                        label = { Text("Jelajahi") }
                    )
                    NavigationBarItem(
                        selected = currentScreen is Screen.Cart,
                        onClick = { viewModel.navigateTo(Screen.Cart) },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (cartItemCount > 0) {
                                        Badge { Text("$cartItemCount") }
                                    }
                                }
                            ) {
                                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Keranjang")
                            }
                        },
                        label = { Text("Keranjang") }
                    )
                    NavigationBarItem(
                        selected = currentScreen is Screen.Profile,
                        onClick = { viewModel.navigateTo(Screen.Profile) },
                        icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profil") },
                        label = { Text("Profil") }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentScreen) {
                is Screen.Home -> HomeScreen(
                    viewModel = viewModel,
                    selectedCategory = selectedCategory,
                    products = viewModel.allProducts,
                    cartItemCount = cartItemCount
                )
                is Screen.Explore -> ExploreScreen(
                    viewModel = viewModel,
                    searchQuery = searchQuery,
                    selectedCategory = selectedCategory,
                    products = viewModel.allProducts
                )
                is Screen.Cart -> CartScreen(
                    viewModel = viewModel,
                    cartItems = cartItems,
                    onBack = { viewModel.navigateTo(Screen.Home) }
                )
                is Screen.Profile -> ProfileScreen(
                    viewModel = viewModel,
                    orders = orders
                )
                is Screen.ProductDetail -> {
                    selectedProduct?.let { product ->
                        ProductDetailScreen(
                            product = product,
                            viewModel = viewModel,
                            onBack = { viewModel.navigateTo(Screen.Home) }
                        )
                    } ?: run {
                        viewModel.navigateTo(Screen.Home)
                    }
                }
                is Screen.Checkout -> CheckoutScreen(
                    viewModel = viewModel,
                    cartItems = cartItems,
                    onBack = { viewModel.navigateTo(Screen.Cart) }
                )
                is Screen.PaymentMethod -> PaymentMethodScreen(
                    viewModel = viewModel,
                    selectedTotal = viewModel.calculateSelectedTotal(),
                    onBack = { viewModel.navigateTo(Screen.Checkout) }
                )
                is Screen.PaymentInstructions -> PaymentInstructionsScreen(
                    viewModel = viewModel,
                    selectedPaymentMethod = selectedPaymentMethod,
                    selectedTotal = viewModel.calculateSelectedTotal(),
                    onBack = { viewModel.navigateTo(Screen.PaymentMethod) }
                )
                is Screen.OrderSuccess -> OrderSuccessScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}
