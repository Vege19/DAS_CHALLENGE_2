package com.example.das_challenge_2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.das_challenge_2.R
import com.example.das_challenge_2.adapters.CartAdapter
import com.example.das_challenge_2.models.ProductModel
import com.example.das_challenge_2.utils.showToast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment() {

    private var products: MutableList<ProductModel> = mutableListOf()
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

}
