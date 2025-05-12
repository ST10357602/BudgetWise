package com.example.budgetwise

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LimitDatabase(context: Context):
SQLiteOpenHelper(context, DatabaseName,null, DatabaseVersion){
    companion object{
        private const val DatabaseName = "limits"
        private const val DatabaseVersion = 1
        private const val TableName = "LimitsTable"
        private const val limitId = "Id"
        private const val Month = "month"
        private const val Amount = "Amount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createLimitsTable = "CREATE TABLE $TableName (" +
                "$limitId INTEGER PRIMARY KEY," +
                "$Month TEXT," +
                "$Amount)"
        db?.execSQL(createLimitsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TableName"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun setLimit(limit: Limit){
        val db = writableDatabase
        val columnValues = ContentValues().apply {
            put(Month, limit.date)
            put(Amount, limit.amount)
        }
        db.insert(TableName,null,columnValues)
    }

    fun retrieveLimits():List<Limit>{
        val db = readableDatabase
        val limits = mutableListOf<Limit>()
        val query = "SELECT * FROM $TableName"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(limitId))
            val month = cursor.getString(cursor.getColumnIndexOrThrow(Month))
            val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(Amount))

            val limit = Limit(id,month,amount)
            limits.add(limit)
        }
        return limits
    }

    fun getTotalLimits():Double{
        val db = writableDatabase
        val query = "SELECT $Month, SUM($Amount) AS $Amount FROM $TableName GROUP BY $Month"
        val cursor = db.rawQuery(query,null)

        var counter = 0.0
        while (cursor.moveToNext()) {
            val month = cursor.getString(cursor.getColumnIndexOrThrow(Month))
            val total = cursor.getDouble(cursor.getColumnIndexOrThrow(Amount))

            counter = counter + total
        }
        return counter
    }

}