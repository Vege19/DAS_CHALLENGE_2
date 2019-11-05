package com.example.das_challenge_2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.ProductModel
import com.example.das_challenge_2.utils.getFirebaseReference
import com.example.das_challenge_2.utils.setImageWithGlide
import com.example.das_challenge_2.utils.showToast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_cart_product.view.*

class CartAdapter(list: List<ProductModel>, context: Context): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var cartProducts: List<ProductModel> = mutableListOf()
    private lateinit var context: Context
    private var total_cost = 0.0

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
        val product = cartProducts[position]

        //Get current cart total cost
        total_cost = 0.0
        getFirebaseReference("cart").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                showToast(context, p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    total_cost = p0.child("cart_total").getValue(Double::class.java)!!
                }
            }
        })

        holder.name.text = product.product_name
        holder.price.text = "$${product.product_price}"
        holder.image.setImageWithGlide(context, product.product_image)
        holder.remove.setOnClickListener {
            removeProduct(product)
            showToast(context, "Removed")
        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = view.cartProductImg
        val remove = view.removeProductBtn
        val name = view.cartProductNameTxt
        val price = view.cartProductPriceTxt
    }

    private fun removeProduct(product: ProductModel) {
        Log.d("Firebasedebug", "i cost" + total_cost.toString())
        total_cost -= product.product_price
        Log.d("Firebasedebug", "f cost" + total_cost.toString())
        //Remove product
        getFirebaseReference("cart/cart_products").child(product.product_cart_id).removeValue()
        //Update cart total cost
        getFirebaseReference("cart").child("cart_total").setValue(total_cost)
        //Update product stock
        getFirebaseReference("products/${product.product_id}/product_stock").setValue(product.product_stock + 1)

    }

}