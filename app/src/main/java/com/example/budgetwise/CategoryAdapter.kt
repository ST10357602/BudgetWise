package com.example.budgetwise

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CategoryAdapter(private var categories:List<CategoryClass>,context: Context) :
RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    class CategoryViewHolder(categoryItemView:View):RecyclerView.ViewHolder(categoryItemView){
        val title = categoryItemView.findViewById<TextView>(R.id.CategoryTitleTextView8)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val categoryView = LayoutInflater.from(parent.context).inflate(R.layout.category_design,parent,false)
        return CategoryViewHolder(categoryView)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.title.text = category.title
    }
    fun refreshData(newCategories:List<CategoryClass>){
        categories = newCategories
        notifyDataSetChanged()
    }
}