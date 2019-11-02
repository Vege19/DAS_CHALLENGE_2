package com.example.das_challenge_2.models

data class ProductModel(val product_id: String = "",
                   val product_name: String = "",
                   val product_description: String = "",
                   val product_category: CategoryModel? = null,
                   val product_stock: Int = 0,
                   val product_price: Double = 0.0,
                   val product_image: String = "")