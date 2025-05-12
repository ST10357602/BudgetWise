package com.example.budgetwise

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryTotalAdapter(private var totals:List<CategoryTotalClass>,context: Context):
RecyclerView.Adapter<CategoryTotalAdapter.CategoryTotalViewHolder>(){

    class CategoryTotalViewHolder(totalItemView:View):RecyclerView.ViewHolder(totalItemView) {
        val title = totalItemView.findViewById<TextView>(R.id.titleTextView9)
        val total = totalItemView.findViewById<TextView>(R.id.amountTextView11)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryTotalViewHolder {
        val totalView = LayoutInflater.from(parent.context).inflate(R.layout.category_total_design,parent,false)
        return CategoryTotalViewHolder(totalView)
    }

    override fun getItemCount(): Int {
        return totals.size
    }

    override fun onBindViewHolder(holder: CategoryTotalViewHolder, position: Int) {
        val categoryTotal = totals[position]
        holder.title.text = categoryTotal.title
        holder.total.text = "R ${categoryTotal.total}"
    }

    fun autoRefresh(newItems:List<CategoryTotalClass>){
        totals  = newItems
        notifyDataSetChanged()
    }
}