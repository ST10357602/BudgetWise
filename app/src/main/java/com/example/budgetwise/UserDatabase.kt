package com.example.budgetwise

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.recyclerview.widget.RecyclerView

class UserDatabase(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null, VERSION) {
    companion object{
        private const val DATABASE_NAME = "Users.db"
        private const val VERSION = 1
        private const val TABLE_NAME = "Users"
        private const val ID_COLUMN = "UserId"
        private const val USERNAME_COLUMN = "Username"
        private const val PASSWORD_COLUMN = "Password"
    }

    // creating table
    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = "CREATE TABLE $TABLE_NAME ($ID_COLUMN INTEGER PRIMARY KEY, $USERNAME_COLUMN TEXT, $PASSWORD_COLUMN TEXT)"
        db?.execSQL(createQuery)
    }

    //method to destroy existing table if the database version number is changed
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun saveUser(user:UserClass){
        val userDatabase = readableDatabase
        val values = ContentValues().apply {
            put(USERNAME_COLUMN,user.email)
            put(PASSWORD_COLUMN,user.password)
        }
        userDatabase.insert(TABLE_NAME,null,values)
        userDatabase.close()
    }

    fun getUser(username:String, password:String):Boolean{
        val userDatabase = readableDatabase
        val columns = "$USERNAME_COLUMN = ? AND $PASSWORD_COLUMN = ?"
        val columnValues = arrayOf(username,password)
        val cursor = userDatabase.query(TABLE_NAME,null,columns,columnValues,null,null,null)

        val doesUserExist = cursor.count > 0
        return doesUserExist
    }
}