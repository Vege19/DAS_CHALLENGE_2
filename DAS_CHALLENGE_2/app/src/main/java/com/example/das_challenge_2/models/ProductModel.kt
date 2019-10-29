package com.example.das_challenge_2.models

class ProductModel(val id: String = "",
                   val name: String = "",
                   val description: String = "",
                   val category: CategoryModel? = null,
                   val price: Double = 0.0,
                   val imageUrl: String = "")