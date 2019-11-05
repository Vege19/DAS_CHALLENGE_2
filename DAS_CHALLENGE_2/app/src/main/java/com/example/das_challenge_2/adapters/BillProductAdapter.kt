package com.example.das_challenge_2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.ProductModel
import kotlinx.android.synthetic.main.item_bill_product.view.*

class BillProductAdapter(products: List<ProductModel>, context: Context) : RecyclerView.Adapter<BillProductAdapter.ViewHolder>() {

    private var products: List<ProductModel> = arrayListOf()
    private var context: Context

    init {
        this.products = products
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bill_product,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: ProductModel = products[position]

        holder.name_product.text = product.product_name
        holder.price_product.text = product.product_price.toString()

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name_product = view.bill_ProductName
        val price_product = view.bill_PriceTxt
    }

}