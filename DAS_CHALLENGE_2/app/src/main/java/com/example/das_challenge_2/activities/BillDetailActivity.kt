package com.example.das_challenge_2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.das_challenge_2.R
import com.example.das_challenge_2.adapters.BillAdapter
import com.example.das_challenge_2.models.ProductModel
import kotlinx.android.synthetic.main.activity_invoice_detail.*

class BillDetailActivity : AppCompatActivity() {

    private var products: MutableList<ProductModel> = mutableListOf()
    private lateinit var adapter: BillAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_detail)

        recycler_prodct_detail.layoutManager = LinearLayoutManager(this)
        adapter = BillAdapter(products,this)
        recycler_prodct_detail.adapter = adapter


    }

    private fun loadDetailProduct(){

    }


}
