package com.example.das_challenge_2.models

data class BillModel(val bill_id: Int = 0,
                     val bill_date: String = "",
                     val bill_client: String = "",
                     val bill_products: List<ProductModel> = arrayListOf(),
                     val bill_total: Double = 0.0)