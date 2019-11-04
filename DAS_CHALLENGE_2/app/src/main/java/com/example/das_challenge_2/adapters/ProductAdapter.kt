package com.example.das_challenge_2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.das_challenge_2.R
import com.example.das_challenge_2.activities.ProductDetailActivity
import com.example.das_challenge_2.models.ProductModel
import com.example.das_challenge_2.utils.Constants
import com.example.das_challenge_2.utils.setImageWithGlide
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(productList: List<ProductModel>, context: Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private var listProduct: List<ProductModel> = arrayListOf()
    private var context: Context

    init {
        this.listProduct = productList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: ProductModel = listProduct[position]

        holder.name.text = product.product_name
        holder.description.text = product.product_description
        holder.price.text = "$${product.product_price}"
        holder.img.setImageWithGlide(context, product.product_image)

        holder.itemView.setOnClickListener {
            goToDetails(product)
        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val img = view.product_img
        val name = view.nameProduct
        var description = view.descriptionProduct
        val price = view.priceProduct
    }

    private fun goToDetails(product: ProductModel) {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra(Constants.PRODUCT_KEY, product)
        context.startActivity(intent)
    }

}
