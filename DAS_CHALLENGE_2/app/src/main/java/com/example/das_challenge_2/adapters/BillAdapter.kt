package com.example.das_challenge_2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.ProductModel
import com.example.das_challenge_2.utils.setImageWithGlide
import kotlinx.android.synthetic.main.item_bill_product.view.*

class BillAdapter(product: List<ProductModel>, context: Context) : RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    private var producto: List<ProductModel> = arrayListOf()
    private var context: Context

    init {
        this.producto = product
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bill_product,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return producto.size
    }

    override fun onBindViewHolder(holder: BillAdapter.ViewHolder, position: Int) {
        val productList: ProductModel = producto[position]

        holder.name_product.text = productList.product_name
        holder.price_product.text = productList.product_price.toString()
        holder.img_product.setImageWithGlide(context,productList.product_image)



    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val img_product = view.bill_Img
        val name_product = view.bill_ProductName
        val price_product = view.bill_PriceTxt

    }

}