package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetwise.databinding.ActivityMoreBinding

class MoreActivity : AppCompatActivity() {

    private lateinit var activityMoreBinding: ActivityMoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMoreBinding = ActivityMoreBinding.inflate(layoutInflater)
        setContentView(activityMoreBinding.root)

        activityMoreBinding.goToCategoriesButton.setOnClickListener {
            val intent = Intent(this,ExpenseCategoriesActivity::class.java)
            startActivity(intent)
        }
        activityMoreBinding.goToCategoriesTotalsButton.setOnClickListener {
            val intent = Intent(this,CategoryTotals::class.java)
            startActivity(intent)
        }
        activityMoreBinding.goToLimitsButton.setOnClickListener {
            val intent = Intent(this,LimitsActivity::class.java)
            startActivity(intent)
        }
        activityMoreBinding.moreButton.setOnClickListener {
            val intent = Intent(this,MoreActivity::class.java)
            startActivity(intent)
        }
        activityMoreBinding.addExpenseButton.setOnClickListener {
            val intent = Intent(this,AddNewExpenseActivity::class.java)
            startActivity(intent)
        }
        activityMoreBinding.reportButton.setOnClickListener {
            val reportIntent = Intent(this,ReportActivity::class.java)
            startActivity(reportIntent)
        }
        activityMoreBinding.homeButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}