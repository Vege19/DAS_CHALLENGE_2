package com.example.das_challenge_2.models

data class CartModel(
    val cart_products: List<ProductModel>,
    val cart_total_items: Int = 0,
    val cart_total: Double = 0.0
)