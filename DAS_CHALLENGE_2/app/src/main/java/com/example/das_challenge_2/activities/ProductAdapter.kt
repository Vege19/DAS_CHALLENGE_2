package com.example.das_challenge_2.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.ProductModel
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(productList: List<ProductModel>, context: Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private var listProduct: List<ProductModel> = arrayListOf()
    private var context: Context

    init {
        this.listProduct = productList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val product: ProductModel = listProduct[position]

        holder.name1.text = product.product_name
        holder.descripcion.text = product.product_description
        holder.precio.text = product.product_price.toString()
        holder.cantidad.text = product.product_stock.toString()
        Glide.with(context)
            .load(product.product_image)
            .into(holder.img1)

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val img1 = view.product_img
        val name1 = view.nameProduct
        var descripcion = view.descriptionProduct
        val precio = view.priceProduct
        val cantidad = view.product_stock

    }

}
