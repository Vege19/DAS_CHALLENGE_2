package com.example.das_challenge_2.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.ProductModel
import com.example.das_challenge_2.utils.Constants
import com.example.das_challenge_2.utils.getFirebaseReference
import com.example.das_challenge_2.utils.setImageWithGlide
import com.example.das_challenge_2.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_cart.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var product: ProductModel
    private var quantity = 0
    private var cartTotal = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        title = "Detalles del producto"
        val myActionBar = supportActionBar
        myActionBar?.setDisplayHomeAsUpEnabled(true)

        //Fetch product model
        product = intent.getSerializableExtra(Constants.PRODUCT_KEY) as ProductModel

        //Getting cart total cost
        FirebaseDatabase.getInstance().getReference("cart/cart_total").addValueEventListener(object:
            ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                showToast(baseContext, p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    cartTotal = p0.getValue(Double::class.java)!!
                    Log.d("FirebaseDebug", "$cartTotal")
                }
            }

        })

        showContent()

        addToCartFab.setOnClickListener {
            showBottomSheet()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showContent() {
        //Image
        productDetailImg.setImageWithGlide(this, product.product_image)
        //Details
        productDetailNameTxt.text = product.product_name
        productDetailDescTxt.text = product.product_description
        productDetailPriceTxt.text = "$${product.product_price}"
        productDetailStockTxt.text = "Stock: ${product.product_stock}"
        //Set category according id
        var categoryString = ""
        when (product.product_category) {
            1 -> categoryString = "Computadoras"
            2 -> categoryString = "Smartphones"
            3 -> categoryString = "Consolas"
            4 -> categoryString = "Accesorios"
        }
        productDetailCategoryTxt.text = categoryString

    }

    private fun showBottomSheet() {
        //Build dialog
        val bottomSheet = BottomSheetDialog(this)
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null)
        bottomSheet.setContentView(bottomSheetView)
        //Bottom sheet events
        bottomSheet.show()
        bottomSheetView.addToCartBtn.setOnClickListener {
            //Save quantity
            quantity = bottomSheetView.productQuantityTxt.text.trim().toString().toInt()
            //Validate if quantity is less or the same than product stock
            if (quantity > product.product_stock) {
                bottomSheetView.productQuantityTxt.error = "Error prro."
            } else {
                addProductsToCart(quantity)
                bottomSheet.dismiss()
            }
        }
    }

    private fun addProductsToCart(quantity: Int) {
        //Fire base references
        val cartProductsReference = getFirebaseReference("cart/cart_products")
        val cartProductStockReference = getFirebaseReference("products/${product.product_id}")
        val cartReference = getFirebaseReference("cart")

        //Add product quantity times
        for (i in 1..quantity) {
            val cartProductId = cartProductsReference.push().key
            product.product_cart_id = cartProductId!!
            cartProductsReference.child(cartProductId).setValue(product)
            cartTotal += product.product_price
        }
        Log.d("FirebaseDebug", "$cartTotal")
        //Update product stock subtracting quantity
        cartProductStockReference.child("product_stock").setValue(product.product_stock - quantity)
        //Update cart total to pay
        cartReference.child("cart_total").setValue(cartTotal)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
