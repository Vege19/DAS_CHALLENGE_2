package com.example.das_challenge_2.models

import java.io.Serializable

data class BillModel(
    val bill_id: String = "",
    val bill_date: String = "",
    val bill_client: String = "",
    val bill_products: List<ProductModel> = arrayListOf(),
    val bill_total: Double = 0.0
): Serializable