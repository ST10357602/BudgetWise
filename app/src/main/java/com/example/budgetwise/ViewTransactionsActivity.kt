package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwise.databinding.ActivityViewTransactionsBinding

class ViewTransactionsActivity : AppCompatActivity() {
    private lateinit var activityViewTransactionsBinding: ActivityViewTransactionsBinding
    private lateinit var expenseDb :ExpenseDatabase
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityViewTransactionsBinding = ActivityViewTransactionsBinding.inflate(layoutInflater)
        setContentView(activityViewTransactionsBinding.root)
        expenseDb = ExpenseDatabase(this)
        expenseAdapter = ExpenseAdapter(expenseDb.returnAllExpenses(),this)

        activityViewTransactionsBinding.expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        activityViewTransactionsBinding.expensesRecyclerView.adapter = expenseAdapter

        activityViewTransactionsBinding.moreButton.setOnClickListener {
            val intent = Intent(this,MoreActivity::class.java)
            startActivity(intent)
        }
        activityViewTransactionsBinding.addExpenseButton.setOnClickListener {
            val intent = Intent(this,AddNewExpenseActivity::class.java)
            startActivity(intent)
        }
        activityViewTransactionsBinding.reportButton.setOnClickListener {
            val reportIntent = Intent(this,ReportActivity::class.java)
            startActivity(reportIntent)
        }
        activityViewTransactionsBinding.homeButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        expenseAdapter.autoRefresh(expenseDb.returnAllExpenses())
    }

}