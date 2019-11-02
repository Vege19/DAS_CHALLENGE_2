package com.example.das_challenge_2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.SingleProductModel
import com.example.das_challenge_2.utils.setImageWithGlide
import com.example.das_challenge_2.utils.showToast
import kotlinx.android.synthetic.main.item_cart_product.view.*

class CartAdapter(list: List<SingleProductModel>, context: Context): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var cartProducts: List<SingleProductModel> = mutableListOf()
    private lateinit var context: Context

    init {
        this.cartProducts = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartProducts.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val singleProduct = cartProducts[position]

        holder.name.text = singleProduct.single_product_name
        holder.price.text = "$${singleProduct.single_product_price}"
        holder.image.setImageWithGlide(context, singleProduct.single_product_image)
        holder.remove.setOnClickListener {
            showToast(context, "Removed")
        }

    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = view.cartProductImg
        val remove = view.removeProductBtn
        val name = view.cartProductNameTxt
        val price = view.cartProductPriceTxt
    }

}