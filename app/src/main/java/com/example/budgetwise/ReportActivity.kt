package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwise.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    private lateinit var activityReportBinding: ActivityReportBinding
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var expenseDatabase: ExpenseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        expenseDatabase = ExpenseDatabase(this)
        expenseAdapter = ExpenseAdapter(expenseDatabase.returnAllExpenses(),this)
        activityReportBinding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(activityReportBinding.root)



        activityReportBinding.transactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        activityReportBinding.transactionsRecyclerView.adapter = expenseAdapter

        activityReportBinding.moreButton.setOnClickListener {
            val intent = Intent(this,MoreActivity::class.java)
            startActivity(intent)
        }
        activityReportBinding.addExpenseButton.setOnClickListener {
            val intent = Intent(this,AddNewExpenseActivity::class.java)
            startActivity(intent)
        }
        activityReportBinding.reportButton.setOnClickListener {
            val reportIntent = Intent(this,ReportActivity::class.java)
            startActivity(reportIntent)
        }
        activityReportBinding.homeButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        expenseAdapter.autoRefresh(expenseDatabase.returnAllExpenses())
    }

    override fun onRestart() {
        super.onRestart()
        expenseAdapter.autoRefresh(expenseDatabase.returnAllExpenses())
    }
}