package com.example.tokoku.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tokoku.model.CartItem
import com.example.tokoku.model.MockData
import com.example.tokoku.model.Order
import com.example.tokoku.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class Screen {
    object Home : Screen()
    object Explore : Screen()
    object Cart : Screen()
    object Profile : Screen()
    object ProductDetail : Screen()
    object Checkout : Screen()
    object PaymentMethod : Screen()
    object PaymentInstructions : Screen()
    object OrderSuccess : Screen()
}

class MarketplaceViewModel : ViewModel() {

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Semua")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    private val _selectedPaymentMethod = MutableStateFlow("BCA Virtual Account")
    val selectedPaymentMethod: StateFlow<String> = _selectedPaymentMethod.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(
        listOf(
            CartItem(MockData.products[0], 1, true),
            CartItem(MockData.products[2], 2, true)
        )
    )
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(
        listOf(
            Order(
                orderId = "TRX-982341",
                date = "15 Okt 2023",
                items = listOf(CartItem(MockData.products[1], 1, true)),
                totalPrice = 8750000.0,
                status = "Selesai",
                shippingAddress = "Jl. Sudirman No. 45, Jakarta Pusat",
                paymentMethod = "BCA Virtual Account"
            )
        )
    )
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    val categories = MockData.categories
    val allProducts = MockData.products

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectProduct(product: Product?) {
        _selectedProduct.value = product
        if (product != null) {
            _currentScreen.value = Screen.ProductDetail
        }
    }

    fun selectPaymentMethod(method: String) {
        _selectedPaymentMethod.value = method
    }

    fun toggleDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        _cartItems.update { currentList ->
            val existingIndex = currentList.indexOfFirst { it.product.id == product.id }
            if (existingIndex >= 0) {
                currentList.mapIndexed { index, item ->
                    if (index == existingIndex) {
                        item.copy(quantity = item.quantity + quantity, isSelected = true)
                    } else {
                        item
                    }
                }
            } else {
                currentList + CartItem(product, quantity, true)
            }
        }
    }

    fun updateCartQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeCartItem(productId)
            return
        }
        _cartItems.update { currentList ->
            currentList.map { item ->
                if (item.product.id == productId) {
                    item.copy(quantity = quantity)
                } else {
                    item
                }
            }
        }
    }

    fun toggleCartItemSelection(productId: String) {
        _cartItems.update { currentList ->
            currentList.map { item ->
                if (item.product.id == productId) {
                    item.copy(isSelected = !item.isSelected)
                } else {
                    item
                }
            }
        }
    }

    fun toggleSelectAllCart(selectAll: Boolean) {
        _cartItems.update { currentList ->
            currentList.map { it.copy(isSelected = selectAll) }
        }
    }

    fun removeCartItem(productId: String) {
        _cartItems.update { currentList ->
            currentList.filter { it.product.id != productId }
        }
    }

    fun calculateSelectedTotal(): Double {
        return _cartItems.value.filter { it.isSelected }.sumOf { it.product.price * it.quantity }
    }

    fun placeOrder(address: String, paymentMethod: String): Boolean {
        val selectedItems = _cartItems.value.filter { it.isSelected }
        if (selectedItems.isEmpty()) return false

        val totalPrice = calculateSelectedTotal() + 15000.0 // + shipping fee
        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
        val dateStr = dateFormat.format(Date())

        val newOrder = Order(
            orderId = "TRX-${(100000..999999).random()}",
            date = dateStr,
            items = selectedItems,
            totalPrice = totalPrice,
            status = "Diproses",
            shippingAddress = address,
            paymentMethod = paymentMethod
        )

        _orders.update { listOf(newOrder) + it }
        // Remove checked out items from cart
        _cartItems.update { currentList -> currentList.filter { !it.isSelected } }
        _currentScreen.value = Screen.OrderSuccess
        return true
    }
}
