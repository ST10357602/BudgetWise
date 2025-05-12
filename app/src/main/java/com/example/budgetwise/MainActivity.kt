package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var categoryTotalAdapter: CategoryTotalAdapter
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var expenseDb:ExpenseDatabase
    private lateinit var limitsDatabase:LimitDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // initializing databases and category totals adapter
        limitsDatabase = LimitDatabase(this)
        expenseDb = ExpenseDatabase(this)
        categoryDatabase = CategoryDatabase(this)
        categoryTotalAdapter = CategoryTotalAdapter(expenseDb.getCategoryTotals(),this)
        expenseAdapter = ExpenseAdapter(expenseDb.returnAllExpenses(),this)

        //setting up the recycler view
        activityMainBinding.homeCategoriesRecyclerView.layoutManager = LinearLayoutManager(this)
        activityMainBinding.homeCategoriesRecyclerView.adapter = categoryTotalAdapter

        //setting the transactions recycler view
        activityMainBinding.transactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        activityMainBinding.transactionsRecyclerView.adapter = expenseAdapter

        // getting the totals to display on Home screen

        val totalLimits = limitsDatabase.getTotalLimits()
        val totalExpenses = expenseDb.getTotalExpenseAmount()
        val balance = totalLimits - totalExpenses

        // attaching amounts to UI elements
        activityMainBinding.expenseTextView.text =  "R ${totalExpenses}"
        activityMainBinding.balanceTextView.text =  "R ${balance}"
        activityMainBinding.limitTextView.text = "R ${totalLimits}"

        activityMainBinding.moreButton.setOnClickListener {
            val intent = Intent(this,MoreActivity::class.java)
            startActivity(intent)
        }
        activityMainBinding.addExpenseButton.setOnClickListener {
            val intent = Intent(this,AddNewExpenseActivity::class.java)
            startActivity(intent)
        }
        activityMainBinding.reportButton.setOnClickListener {
            val reportIntent = Intent(this,ReportActivity::class.java)
            startActivity(reportIntent)
        }
        activityMainBinding.homeButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        expenseAdapter.autoRefresh(expenseDb.returnAllExpenses())
        categoryTotalAdapter.autoRefresh(expenseDb.getCategoryTotals())
        val totalLimits = limitsDatabase.getTotalLimits()
        val totalExpenses = expenseDb.getTotalExpenseAmount()
        val balance = totalLimits - totalExpenses

        // attaching amounts to UI elements
        activityMainBinding.expenseTextView.text =  "R ${totalExpenses}"
        activityMainBinding.balanceTextView.text =  "R ${balance}"
        activityMainBinding.limitTextView.text = "R ${totalLimits}"
    }
}