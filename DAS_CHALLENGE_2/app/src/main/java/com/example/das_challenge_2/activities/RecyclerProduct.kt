package com.example.das_challenge_2.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_recycler_category.*

class RecyclerProduct : AppCompatActivity() {

    private var product: MutableList<ProductModel> = mutableListOf()
    private lateinit var adapter_product: ProductAdapter
    private val TAG = "MyActivity"


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_recycler_category)

        productList.layoutManager = LinearLayoutManager(this)
        adapter_product = ProductAdapter(product,this)
        productList.adapter = adapter_product

        LoadProducts()
    }

    private fun LoadProducts(){
        FirebaseDatabase.getInstance().getReference("products").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                //Toast.makeText(requireContext(),"",Toast.LENGTH_SHORT).show()
                Log.d(TAG,p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (product.size > 0) {
                    //Clean the list if it contains data
                    product.clear()
                }

                if(p0.exists()){
                    for (tmp in p0.children){
                        val producto = tmp.getValue(ProductModel::class.java)
                        product.add(producto!!)
                        adapter_product.notifyDataSetChanged()
                    }
                }

            }
        })
    }
}