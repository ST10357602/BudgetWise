package com.example.budgetwise

data class Expense(val expenseId:Int,val categoryTitle:String,val description:String,val date:String,val amount:Double,
    val transactionPicture:ByteArray)
