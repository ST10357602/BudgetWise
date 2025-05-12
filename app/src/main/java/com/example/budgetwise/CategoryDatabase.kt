package com.example.budgetwise

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CategoryDatabase(context: Context):SQLiteOpenHelper(context, DatabaseName,null,
    DatabaseVersion) {
    companion object{
        private const val DatabaseName = "Categories"
        private const val DatabaseVersion = 1
        private const val TableName = "Categories"
        private const val ID = "id"
        private const val Title = "title"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createCategoryTable = "CREATE TABLE $TableName ($ID INTEGER PRIMARY KEY, $Title TEXT)"
        db?.execSQL(createCategoryTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropCategoryTable = "DROP TABLE IF EXISTS $TableName"
        db?.execSQL(dropCategoryTable)
        onCreate(db)
    }

    fun createCategory(category: CategoryClass){
        val categoryDatabase = writableDatabase
        val columnValues = ContentValues().apply {

            put(Title,category.title)
        }
        categoryDatabase.insert(TableName,null,columnValues)
    }
    fun returnCategories():List<CategoryClass>{
        val categoryDb = readableDatabase
        val categoryList = mutableListOf<CategoryClass>()
        val selectQuery = "SELECT * FROM $TableName"
        val cursor = categoryDb.rawQuery(selectQuery,null)

        while (cursor.moveToNext()){
            val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(Title))

            val categoryObject = CategoryClass(categoryId,title)
            categoryList.add(categoryObject)
        }
        cursor.close()
        categoryDb.close()
        return categoryList
    }
    fun getCategoryDescriptions(categories:List<CategoryClass>):List<String>{
        val descriptions = mutableListOf<String>()
        var i = 0;
        while (i < categories.size){
            val description = categories[i].title
            descriptions.add(description)
            i++
        }
        return descriptions
    }
}