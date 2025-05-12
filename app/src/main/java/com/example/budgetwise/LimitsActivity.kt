package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwise.databinding.ActivityLimitsBinding

class LimitsActivity : AppCompatActivity() {
    private lateinit var activityLimitsBinding: ActivityLimitsBinding
    private lateinit var limitAdapter: LimitAdapter
    private lateinit var limitDatabase: LimitDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityLimitsBinding = ActivityLimitsBinding.inflate(layoutInflater)
        setContentView(activityLimitsBinding.root)
        limitDatabase = LimitDatabase(this)
        limitAdapter = LimitAdapter(limitDatabase.retrieveLimits(),this)

        activityLimitsBinding.limitsRecyclerView.layoutManager = LinearLayoutManager(this)
        activityLimitsBinding.limitsRecyclerView.adapter = limitAdapter

        activityLimitsBinding.AddNewLimitFloatingActionButton.setOnClickListener {
            val intent = Intent(this,SetLimitsActivity::class.java)
            startActivity(intent)
        }

        activityLimitsBinding.moreButton.setOnClickListener {
            val intent = Intent(this,MoreActivity::class.java)
            startActivity(intent)
        }
        activityLimitsBinding.addExpenseButton.setOnClickListener {
            val intent = Intent(this,AddNewExpenseActivity::class.java)
            startActivity(intent)
        }
        activityLimitsBinding.reportButton.setOnClickListener {
            val reportIntent = Intent(this,ReportActivity::class.java)
            startActivity(reportIntent)
        }
        activityLimitsBinding.homeButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        limitAdapter.refreshData(limitDatabase.retrieveLimits())
    }
}