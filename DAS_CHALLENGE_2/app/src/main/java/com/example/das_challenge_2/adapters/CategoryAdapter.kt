package com.example.das_challenge_2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.CategoryModel
import com.example.das_challenge_2.utils.setImageWithGlide
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(list: List<CategoryModel>, context: Context): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categories: List<CategoryModel> = arrayListOf()
    private var context: Context

    init {
        this.categories = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val category = categories[position]

        holder.title.text = category.category_name
        holder.image.setImageWithGlide(context, category.category_img)

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image = view.categoryImg
        val title = view.categoryNameTxt
    }

}