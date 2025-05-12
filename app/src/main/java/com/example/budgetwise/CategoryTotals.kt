package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwise.databinding.ActivityCategoryTotalsBinding
import kotlin.math.exp

class CategoryTotals : AppCompatActivity() {
    private lateinit var activityCategoryTotalsBinding: ActivityCategoryTotalsBinding
    private lateinit var categoryTotalAdapter: CategoryTotalAdapter
    private lateinit var expenseDb:ExpenseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        expenseDb = ExpenseDatabase(this)
        categoryTotalAdapter = CategoryTotalAdapter(expenseDb.getCategoryTotals(),this)
        activityCategoryTotalsBinding = ActivityCategoryTotalsBinding.inflate(layoutInflater)
        setContentView(activityCategoryTotalsBinding.root)
        activityCategoryTotalsBinding.categoryTotalsRecyclerView.layoutManager = LinearLayoutManager(this)
        activityCategoryTotalsBinding.categoryTotalsRecyclerView.adapter = categoryTotalAdapter

        activityCategoryTotalsBinding.moreButton.setOnClickListener {
            val intent = Intent(this,MoreActivity::class.java)
            startActivity(intent)
        }
        activityCategoryTotalsBinding.addExpenseButton.setOnClickListener {
            val intent = Intent(this,AddNewExpenseActivity::class.java)
            startActivity(intent)
        }
        activityCategoryTotalsBinding.reportButton.setOnClickListener {
            val reportIntent = Intent(this,ReportActivity::class.java)
            startActivity(reportIntent)
        }
        activityCategoryTotalsBinding.homeButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        categoryTotalAdapter.autoRefresh(expenseDb.getCategoryTotals())
    }

    override fun onRestart() {
        super.onRestart()
        categoryTotalAdapter.autoRefresh(expenseDb.getCategoryTotals())

    }

}