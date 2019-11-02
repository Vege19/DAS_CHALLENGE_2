package com.example.das_challenge_2.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.das_challenge_2.R
import com.example.das_challenge_2.adapters.CartAdapter
import com.example.das_challenge_2.models.CartModel
import com.example.das_challenge_2.models.SingleProductModel
import com.example.das_challenge_2.utils.showToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_cart.*
import java.util.*

class CartFragment : Fragment() {

    private var products: MutableList<SingleProductModel> = mutableListOf()
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
                        val product = tmp.getValue(SingleProductModel::class.java)
                        products.add(product!!)
                        //Update the list every time that data changes
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    private fun loadTotalCost() {
        FirebaseDatabase.getInstance().getReference("cart/cart_total").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                showToast(requireContext(), p0.message)
            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(p0: DataSnapshot) {
                val total = p0.getValue(Double::class.java)
                cartTotalTxt.text = "Total: $$total"
            }

        })
    }

}
