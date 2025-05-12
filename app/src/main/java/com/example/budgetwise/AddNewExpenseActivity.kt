package com.example.budgetwise

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetwise.databinding.ActivityAddNewExpenseBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddNewExpenseActivity : AppCompatActivity() {
    private lateinit var activityAddNewExpenseBinding: ActivityAddNewExpenseBinding
    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var expenseDatabase: ExpenseDatabase
    private lateinit var categorySpinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        categoryDatabase = CategoryDatabase(this)
        expenseDatabase = ExpenseDatabase(this)
        activityAddNewExpenseBinding = ActivityAddNewExpenseBinding.inflate(layoutInflater)
        setContentView(activityAddNewExpenseBinding.root)

        categorySpinner = activityAddNewExpenseBinding.categoriesSpinner

        // logic for pressing the camera icon to take image of receipt
        activityAddNewExpenseBinding.imageView7.setOnClickListener {
            val launchCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(launchCamera)
        }

        //buttons for navigation
        activityAddNewExpenseBinding.moreButton.setOnClickListener {
            val intent = Intent(this,MoreActivity::class.java)
            startActivity(intent)
        }
        activityAddNewExpenseBinding.addExpenseButton.setOnClickListener {
            val intent = Intent(this,AddNewExpenseActivity::class.java)
            startActivity(intent)
        }
        activityAddNewExpenseBinding.reportButton.setOnClickListener {
            val reportIntent = Intent(this,ReportActivity::class.java)
            startActivity(reportIntent)
        }
        activityAddNewExpenseBinding.homeButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        // logic to set category items from category DB and displaying them in a spinner
        val categoriesObjects = categoryDatabase.returnCategories()
        val categoriesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,categoryDatabase.getCategoryDescriptions(categoriesObjects))
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoriesAdapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                activityAddNewExpenseBinding.CategoryTextView9.text = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val dateDialog = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            dateDialog.set(Calendar.DAY_OF_MONTH,day)
            dateDialog.set(Calendar.MONTH,month)
            dateDialog.set(Calendar.YEAR,year)

            val dateFormat = "yyyy-MM-dd"
            val simple = SimpleDateFormat(dateFormat, Locale.UK)
            activityAddNewExpenseBinding.dateTextView8.text = simple.format(dateDialog.time)

        }
        activityAddNewExpenseBinding.calendaImageView6.setOnClickListener {
            DatePickerDialog(this,date,dateDialog.get(Calendar.YEAR),dateDialog.get(Calendar.MONTH),dateDialog.get(Calendar.DAY_OF_MONTH)).show()

        }

        activityAddNewExpenseBinding.createExpenseButton.setOnClickListener {
            val categoryTitle = activityAddNewExpenseBinding.CategoryTextView9.text.toString()
            val description = activityAddNewExpenseBinding.descritpionEditText.text.toString()
            val date = activityAddNewExpenseBinding.dateTextView8.text.toString()
            val amount = activityAddNewExpenseBinding.amountEditTextNumberDecimal.text.toString().toDouble()
            val receiptImage = pictureToByteArray(activityAddNewExpenseBinding.ReceiptPictureImageView8)

            val newExpense = Expense(0,categoryTitle,description,date,amount,receiptImage)
            expenseDatabase.saveExpense(newExpense)
            Toast.makeText(this,"Expense Added",Toast.LENGTH_LONG).show()
            val reportIntent = Intent(this,ReportActivity::class.java)
            startActivity(reportIntent)
        }


    }

    // method to launch camera. Adapted from ChatGPT AI
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result?.data != null) {
                    val bitmap = result.data?.extras?.get("data") as Bitmap

                    activityAddNewExpenseBinding.ReceiptPictureImageView8.setImageBitmap(bitmap)
                }
            }
        }


    //method to convert imageview into a byteArray. Adapted from ChatGPT AI
    fun pictureToByteArray(imageView: ImageView): ByteArray {

        val drawable = imageView.drawable
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}