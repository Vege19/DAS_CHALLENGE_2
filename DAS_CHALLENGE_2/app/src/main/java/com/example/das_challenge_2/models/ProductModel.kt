package com.example.das_challenge_2.models
import java.io.Serializable

data class ProductModel(
    var product_id: String = "",
    var product_cart_id: String = "",
    val product_name: String = "",
    val product_description: String = "",
    val product_stock: Int = 0,
    val product_category: Int = 0,
    val product_price: Double = 0.0,
    val product_image: String = ""
): Serializable
