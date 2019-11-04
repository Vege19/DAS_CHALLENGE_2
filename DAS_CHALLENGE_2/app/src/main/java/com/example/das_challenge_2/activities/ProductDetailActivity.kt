package com.example.das_challenge_2.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.ProductModel
import com.example.das_challenge_2.utils.Constants
import com.example.das_challenge_2.utils.setImageWithGlide
import com.example.das_challenge_2.utils.showToast
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var product: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        //Fetch product model
        product = intent.getSerializableExtra(Constants.PRODUCT_KEY) as ProductModel

        showContent()

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

}
