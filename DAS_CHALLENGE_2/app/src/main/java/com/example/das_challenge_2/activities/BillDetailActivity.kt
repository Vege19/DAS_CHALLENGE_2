package com.example.das_challenge_2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.das_challenge_2.R
import com.example.das_challenge_2.adapters.BillAdapter
import com.example.das_challenge_2.adapters.BillProductAdapter
import com.example.das_challenge_2.models.BillModel
import com.example.das_challenge_2.models.ProductModel
import com.example.das_challenge_2.utils.Constants
import com.example.das_challenge_2.utils.getFirebaseReference
import com.example.das_challenge_2.utils.showToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_invoice_detail.*

class BillDetailActivity : AppCompatActivity() {

    private var products: MutableList<ProductModel> = mutableListOf()
    private lateinit var adapter: BillProductAdapter
    private lateinit var bill: BillModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_detail)
        toolbarConfig()

        bill = intent.getSerializableExtra(Constants.BILL_KEY) as BillModel

        recycler_prodct_detail.layoutManager = LinearLayoutManager(this)
        adapter = BillProductAdapter(products,this)
        recycler_prodct_detail.adapter = adapter

        loadDetailProduct()
        loadBillProducts()

    }

    private fun loadDetailProduct(){
        //Set bill data
        productDate.text = bill.bill_date
        nameClient.text = bill.bill_client
        billDetailTotalTxt.text = "Total de factura: $${bill.bill_total}"
    }

    private fun loadBillProducts() {
        val reference = getFirebaseReference("bills/${bill.bill_id}/bill_products")

        reference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                showToast(baseContext, p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (products.size > 0) {
                    products.clear()
                }

                if (p0.exists()) {
                    for (tmp in p0.children) {
                        val product = tmp.getValue(ProductModel::class.java)
                        products.add(product!!)
                        Log.d("debug", products.toString())
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

    private fun toolbarConfig() {
        billDetailToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        billDetailToolbar.setNavigationOnClickListener {
            finish()
        }
    }

}
