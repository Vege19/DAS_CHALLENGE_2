package com.example.das_challenge_2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.das_challenge_2.R
import com.example.das_challenge_2.adapters.ProductAdapter
import com.example.das_challenge_2.models.ProductModel
import com.example.das_challenge_2.utils.Constants
import com.example.das_challenge_2.utils.showToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_products.*

class ProductsActivity : AppCompatActivity() {

    private var products: MutableList<ProductModel> = mutableListOf()
    private lateinit var adapter: ProductAdapter
    private var category: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        //Fetch category id
        category = intent.extras!!.getInt(Constants.CATEGORY_KEY)

        productList.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(products, this)
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
                        //Add and show product according selected category
                        if (product?.product_category == category) {
                            products.add(product)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

}