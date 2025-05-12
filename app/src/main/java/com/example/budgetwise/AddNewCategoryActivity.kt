package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetwise.databinding.ActivityAddNewCategoryBinding

class AddNewCategoryActivity : AppCompatActivity() {

    private lateinit var activityAddNewCategoryBinding: ActivityAddNewCategoryBinding
    private lateinit var categoryDb : CategoryDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityAddNewCategoryBinding = ActivityAddNewCategoryBinding.inflate(layoutInflater)
        categoryDb = CategoryDatabase(this)
        setContentView(activityAddNewCategoryBinding.root)

        activityAddNewCategoryBinding.SaveCategoryButton.setOnClickListener {
            val title = activityAddNewCategoryBinding.CategoryDescriptionEditText.text.toString()
            if(title!=""){
                val category = CategoryClass(0,title)
                categoryDb.createCategory(category)
                Toast.makeText(this,"Category $title Save Successfully",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Title field is required",Toast.LENGTH_SHORT).show()
            }

            activityAddNewCategoryBinding.moreButton.setOnClickListener {
                val intent = Intent(this,MoreActivity::class.java)
                startActivity(intent)
            }
            activityAddNewCategoryBinding.addExpenseButton.setOnClickListener {
                val intent = Intent(this,AddNewExpenseActivity::class.java)
                startActivity(intent)
            }
            activityAddNewCategoryBinding.reportButton.setOnClickListener {
                val reportIntent = Intent(this,ReportActivity::class.java)
                startActivity(reportIntent)
            }
            activityAddNewCategoryBinding.homeButton.setOnClickListener {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
}