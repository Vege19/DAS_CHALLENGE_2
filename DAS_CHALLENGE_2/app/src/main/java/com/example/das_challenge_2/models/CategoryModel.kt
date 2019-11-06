package com.example.das_challenge_2.models

import java.io.Serializable

data class CategoryModel(
    val category_id: Int = 0,
    val category_name: String = "",
    val category_img: String = ""
): Serializable
