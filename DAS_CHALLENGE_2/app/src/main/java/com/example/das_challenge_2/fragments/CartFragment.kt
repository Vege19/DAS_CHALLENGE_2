package com.example.das_challenge_2.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.das_challenge_2.R
import com.example.das_challenge_2.activities.ScannerActivity
import com.example.das_challenge_2.adapters.CartAdapter
import com.example.das_challenge_2.models.ProductModel
import com.example.das_challenge_2.utils.getFirebaseReference
import com.example.das_challenge_2.utils.showToast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_cart.*
import androidx.appcompat.widget.Toolbar;

class CartFragment : Fragment() {

    private var products: MutableList<ProductModel> = mutableListOf()
    private lateinit var adapter: CartAdapter
    private var codebarString: String = ""
    private var cartTotal = 0.0
    private var stock = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        openScannerActivity()

        //Getting cart total cost
        FirebaseDatabase.getInstance().getReference("cart/cart_total").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                showToast(requireContext(), p0.message)
            }
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    cartTotal = p0.getValue(Double::class.java)!!
                }
            }
        })

        //Getting product stock
        getFirebaseReference("products/$codebarString/product_stock").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                showToast(requireContext(), p0.message)
            }
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    stock = (p0.getValue(Int::class.java)!!)
                }
            }
        })

        cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(products, requireContext())
        cartRecyclerView.adapter = adapter

        loadCartProducts()
        loadTotalCost()

    }

    private fun loadCartProducts() {
        //Fetch categories from fire base
        FirebaseDatabase.getInstance().getReference("cart/cart_products").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                showToast(requireContext(), p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (products.size > 0) {
                    //Clean the list if it contains data
                    products.clear()
                }

                if (p0.exists()) {
                    for (tmp in p0.children) {
                        //Get categories and add them to the list
                        val product = tmp.getValue(ProductModel::class.java)
                        products.add(product!!)
                        //Update the list every time that data changes
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })

        //Update list of products when these are removed
        getFirebaseReference("cart").addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                adapter.notifyDataSetChanged()
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                adapter.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                adapter.notifyDataSetChanged()
            }

        })
    }

    private fun loadTotalCost() {
        FirebaseDatabase.getInstance().getReference("cart/cart_total").addValueEventListener(object:
            ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                showToast(requireContext(), p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    try {
                        val total = p0.getValue(Double::class.java)
                        cartTotalTxt.text = "Total: $$total"
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_scan -> {
                startActivityForResult(Intent(requireContext(), ScannerActivity::class.java), 1)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun openScannerActivity() {
        cartToolbar.setOnMenuItemClickListener(object: Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                return when (item?.itemId) {
                    R.id.option_scan -> {
                        startActivityForResult(Intent(requireContext(), ScannerActivity::class.java), 1)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                codebarString = data?.data.toString()
                showToast(requireContext(), codebarString)
                foundProduct()
            }
        }

    }

    private fun foundProduct() {
        val productsReference = getFirebaseReference("products")

        productsReference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                showToast(requireContext(), p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (tmp in p0.children) {
                        //Find product according product id
                        val product = tmp.getValue(ProductModel::class.java)
                        //Log.d("Debug", "\nFirebaseProductId: ${product?.product_id}\nBarcodeId: $codebarString")
                        if (product?.product_id == codebarString) {
                            addFoundProductToCart(product)
                        }
                    }
                }
            }
        })

        getFirebaseReference("products/$codebarString/product_stock").setValue(stock)

    }

    private fun addFoundProductToCart(product: ProductModel) {
        val cartProductsReference = getFirebaseReference("cart/cart_products")
        val cartProductId = cartProductsReference.push().key
        product.product_cart_id = cartProductId!!
        cartProductsReference.child(cartProductId).setValue(product)
        //Update price
        getFirebaseReference("cart/cart_total").setValue(cartTotal + product.product_price)
    }

}
