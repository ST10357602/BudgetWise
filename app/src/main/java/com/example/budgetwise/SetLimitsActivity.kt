package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetwise.databinding.ActivityLimitsBinding
import com.example.budgetwise.databinding.ActivitySetLimitsBinding

class SetLimitsActivity : AppCompatActivity() {

    private lateinit var activitySetLimitsBinding: ActivitySetLimitsBinding
    private lateinit var expenseLimitDatabase: LimitDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activitySetLimitsBinding = ActivitySetLimitsBinding.inflate(layoutInflater)
        setContentView(activitySetLimitsBinding.root)
        expenseLimitDatabase = LimitDatabase(this)

        activitySetLimitsBinding.SaveLimitButton.setOnClickListener {
            val month = activitySetLimitsBinding.monthEditText.text.toString()
            val limitAmount = activitySetLimitsBinding.AmountLimitTextNumberDecimal.text.toString()

            if(month =="" && limitAmount ==""){
                Toast.makeText(this,"All fields are required",Toast.LENGTH_SHORT).show()
            }else{
                val limit = Limit(0,month,limitAmount.toDouble())
                expenseLimitDatabase.setLimit(limit)
                Toast.makeText(this,"Limit Set Successfully",Toast.LENGTH_SHORT).show()

                val intent = Intent(this,LimitsActivity::class.java)
                startActivity(intent)
            }

        }

        activitySetLimitsBinding.moreButton.setOnClickListener {
            val intent = Intent(this,MoreActivity::class.java)
            startActivity(intent)
        }
        activitySetLimitsBinding.addExpenseButton.setOnClickListener {
            val intent = Intent(this,AddNewExpenseActivity::class.java)
            startActivity(intent)
        }
        activitySetLimitsBinding.reportButton.setOnClickListener {
            val reportIntent = Intent(this,ReportActivity::class.java)
            startActivity(reportIntent)
        }
        activitySetLimitsBinding.homeButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}