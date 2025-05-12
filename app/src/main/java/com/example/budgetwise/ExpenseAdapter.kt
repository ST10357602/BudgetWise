package com.example.budgetwise

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ExpenseAdapter(private var expenses:List<Expense>,context: Context):
RecyclerView.Adapter<ExpenseAdapter.TransactionViewHolder>(){

    class TransactionViewHolder(expenseViewItem:View):RecyclerView.ViewHolder(expenseViewItem) {
        val receiptImage = expenseViewItem.findViewById<ImageView>(R.id.ReceiptImageView6)
        val categoryTitle = expenseViewItem.findViewById<TextView>(R.id.TitleTextView8)
        val amountText = expenseViewItem.findViewById<TextView>(R.id.AmountTextView8)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val expenseView = LayoutInflater.from(parent.context).inflate(R.layout.expense_design,parent,false)
        return TransactionViewHolder(expenseView)
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val expense = expenses[position]
        val image = expense.transactionPicture
        holder.receiptImage.setImageBitmap( BitmapFactory.decodeByteArray(image, 0, image.size))
        holder.amountText.text = "R ${expense.amount}"
        holder.categoryTitle.text = expense.categoryTitle
    }
    fun autoRefresh(latestExpenses: List<Expense>) {
        expenses = latestExpenses
        notifyDataSetChanged()
    }
}