package com.example.das_challenge_2.models

data class CartModel(val cart_products: List<SingleProductModel>,
                     val cart_total: Double = 0.0)