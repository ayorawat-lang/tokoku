package com.example.tokoku.model

data class CartItem(
    val product: Product,
    var quantity: Int = 1,
    var isSelected: Boolean = true
)
