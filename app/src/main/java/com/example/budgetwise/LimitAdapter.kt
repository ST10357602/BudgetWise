package com.example.budgetwise

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LimitAdapter(private var limits:List<Limit>,context: Context):
RecyclerView.Adapter<LimitAdapter.LimitViewHolder>(){

    class LimitViewHolder(limitItemView: View):RecyclerView.ViewHolder(limitItemView) {
        val monthText = limitItemView.findViewById<TextView>(R.id.MonthTextView8)
        val amount = limitItemView.findViewById<TextView>(R.id.AmountTotalTextView8)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LimitViewHolder {
        val limitView = LayoutInflater.from(parent.context).inflate(R.layout.limit_design,parent,false)
        return LimitViewHolder(limitView)
    }

    override fun getItemCount(): Int {
        return limits.size
    }

    override fun onBindViewHolder(holder: LimitViewHolder, position: Int) {
        val limit = limits[position]
        holder.monthText.text = "${limit.date} Limit"
        holder.amount.text = "R ${limit.amount}"
    }

    fun refreshData(newItems:List<Limit>){
        limits = newItems
        notifyDataSetChanged()
    }
}