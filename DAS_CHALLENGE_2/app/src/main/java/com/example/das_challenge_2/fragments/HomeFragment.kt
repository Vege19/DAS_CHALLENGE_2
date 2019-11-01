package com.example.das_challenge_2.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.das_challenge_2.R
import com.example.das_challenge_2.adapters.CategoryAdapter
import com.example.das_challenge_2.models.CategoryModel
import com.example.das_challenge_2.utils.showToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var categories: MutableList<CategoryModel> = mutableListOf()
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Configure recycler view
        categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CategoryAdapter(categories, requireContext())
        categoriesRecyclerView.adapter = adapter

        loadCategories()

    }

    private fun loadCategories() {
        //Fetch categories from fire base
        FirebaseDatabase.getInstance().getReference("categories").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                showToast(requireContext(), p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (categories.size > 0) {
                    //Clean the list if it contains data
                    categories.clear()
                }

                if (p0.exists()) {
                    for (tmp in p0.children) {
                        //Get categories and add them to the list
                        val category = tmp.getValue(CategoryModel::class.java)
                        categories.add(category!!)
                        //Update the list every time that data changes
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

}
