package com.example.tokoku.model

data class Order(
    val orderId: String,
    val date: String,
    val items: List<CartItem>,
    val totalPrice: Double,
    val status: String, // "Selesai", "Diproses", "Dikirim"
    val shippingAddress: String,
    val paymentMethod: String = "Transfer Bank BCA"
)
