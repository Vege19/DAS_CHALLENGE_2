package com.example.das_challenge_2.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.ProductModel
import com.example.das_challenge_2.utils.showToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_recycler_category.*

class ProductsActivity : AppCompatActivity() {

    private var products: MutableList<ProductModel> = mutableListOf()
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_category)

        productList.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(products,this)
        productList.adapter = adapter

        loadProducts()

    }

    private fun loadProducts(){
        FirebaseDatabase.getInstance().getReference("products").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
               showToast(baseContext, p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (products.size > 0) {
                    //Clean the list if it contains data
                    products.clear()
                }

                if(p0.exists()){
                    for (tmp in p0.children){
                        val product = tmp.getValue(ProductModel::class.java)
                        products.add(product!!)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

}