package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwise.databinding.ActivityExpenseCategoriesBinding

class ExpenseCategoriesActivity : AppCompatActivity() {

    private lateinit var categoryDb : CategoryDatabase
    private lateinit var activityExpenseCategoriesBinding: ActivityExpenseCategoriesBinding
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        categoryDb = CategoryDatabase(this)
        categoryAdapter = CategoryAdapter(categoryDb.returnCategories(),this)
        activityExpenseCategoriesBinding = ActivityExpenseCategoriesBinding.inflate(layoutInflater)
        setContentView(activityExpenseCategoriesBinding.root)

        activityExpenseCategoriesBinding.categoriesRecyclerView.layoutManager = LinearLayoutManager(this)
        activityExpenseCategoriesBinding.categoriesRecyclerView.adapter = categoryAdapter

        activityExpenseCategoriesBinding.AddNewCategoryFloatingActionButton.setOnClickListener {
            val intent = Intent(this,AddNewCategoryActivity::class.java)
            startActivity(intent)
        }

        activityExpenseCategoriesBinding.moreButton.setOnClickListener {
            val intent = Intent(this,MoreActivity::class.java)
            startActivity(intent)
        }
        activityExpenseCategoriesBinding.addExpenseButton.setOnClickListener {
            val intent = Intent(this,AddNewExpenseActivity::class.java)
            startActivity(intent)
        }
        activityExpenseCategoriesBinding.reportButton.setOnClickListener {
            val reportIntent = Intent(this,ReportActivity::class.java)
            startActivity(reportIntent)
        }
        activityExpenseCategoriesBinding.homeButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        categoryAdapter.refreshData(categoryDb.returnCategories())
    }
}