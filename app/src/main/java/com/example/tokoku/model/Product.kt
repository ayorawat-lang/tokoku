package com.example.tokoku.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val originalPrice: Double? = null,
    val rating: Float,
    val soldCount: Int,
    val category: String,
    val description: String,
    val imageUrl: String,
    val storeName: String,
    val storeLocation: String,
    val sizes: List<String> = listOf("S", "M", "L"),
    val colors: List<String> = listOf("Hijau", "Biru", "Merah", "Kuning")
)
