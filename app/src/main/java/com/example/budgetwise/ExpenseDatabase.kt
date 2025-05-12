package com.example.budgetwise

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabase(context: Context):
SQLiteOpenHelper(context, DatabaseName,null, DatabaseVersion){

    companion object{
        private const val DatabaseName = "Expenses"
        private const val DatabaseVersion = 2
        private const val TableName = "ExpenseTable"
        private const val ExpenseId = "ExpenseId"
        private const val CategoryTitle = "Category"
        private const val Description = "Description"
        private const val Date = "Date"
        private const val Amount = "Amount"
        private const val ReceiptImage = "ReceiptImage"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createExpenseTable = "CREATE TABLE $TableName(" +
                "$ExpenseId INTEGER PRIMARY KEY," +
                "$CategoryTitle TEXT," +
                "$Description TEXT," +
                "$Date TEXT," +
                "$Amount DOUBLE," +
                "$ReceiptImage BLOB)"
        db?.execSQL(createExpenseTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableDb = "DROP TABLE IF EXISTS $TableName"
        db?.execSQL(dropTableDb)
        onCreate(db)
    }

    fun returnAllExpenses():List<Expense>{
        val expenseDb = readableDatabase
        val expenses = mutableListOf<Expense>()
        val selectQuery = "SELECT * FROM $TableName"
        val getExpenseCursor = expenseDb.rawQuery(selectQuery,null)

        while (getExpenseCursor.moveToNext()){
            val expenseId = getExpenseCursor.getInt(getExpenseCursor.getColumnIndexOrThrow(ExpenseId))
            val categoryTitle = getExpenseCursor.getString(getExpenseCursor.getColumnIndexOrThrow(
                CategoryTitle))
            val description = getExpenseCursor.getString(getExpenseCursor.getColumnIndexOrThrow(
                Description))
            val date = getExpenseCursor.getString(getExpenseCursor.getColumnIndexOrThrow(Date))
            val amount = getExpenseCursor.getDouble(getExpenseCursor.getColumnIndexOrThrow(Amount))
            val receiptPicture = getExpenseCursor.getBlob(getExpenseCursor.getColumnIndexOrThrow(
                ReceiptImage))

            val expense = Expense(expenseId,categoryTitle,description,date,amount,receiptPicture)
            expenses.add(expense)
        }
        return expenses
    }

    fun saveExpense(expense:Expense){
        val expenseDb = writableDatabase
        val columnValues = ContentValues().apply {
            put(CategoryTitle,expense.categoryTitle)
            put(Description,expense.description)
            put(Date,expense.date)
            put(Amount,expense.amount)
            put(ReceiptImage,expense.transactionPicture)
        }
        expenseDb.insert(TableName,null,columnValues)
        expenseDb.close()

    }

    fun getCategoryTotals():List<CategoryTotalClass>{
        val expenseDb = readableDatabase
        val totalsList = mutableListOf<CategoryTotalClass>()
        val query = "SELECT $CategoryTitle, SUM($Amount) AS $Amount FROM $TableName" +
                " GROUP BY $CategoryTitle"
        val getExpenseCursor = expenseDb.rawQuery(query,null)

        var counter =0
        while (getExpenseCursor.moveToNext()){

            val categoryTitle = getExpenseCursor.getString(getExpenseCursor.getColumnIndexOrThrow(CategoryTitle))
            val amount = getExpenseCursor.getDouble(getExpenseCursor.getColumnIndexOrThrow(Amount))
            val expense = CategoryTotalClass(counter,categoryTitle,amount)
            totalsList.add(expense)
        }
        return totalsList
    }

    fun getTotalExpenseAmount():Double{
        val expenseDb = readableDatabase

        val query = "SELECT $CategoryTitle, SUM($Amount) AS $Amount FROM $TableName" +
                " GROUP BY $CategoryTitle"
        val getExpenseCursor = expenseDb.rawQuery(query,null)

        var counter =0.0
        while (getExpenseCursor.moveToNext()){

            val categoryTitle = getExpenseCursor.getString(getExpenseCursor.getColumnIndexOrThrow(CategoryTitle))
            val amount = getExpenseCursor.getDouble(getExpenseCursor.getColumnIndexOrThrow(Amount))
            counter = counter + amount
        }
        return counter
    }

}