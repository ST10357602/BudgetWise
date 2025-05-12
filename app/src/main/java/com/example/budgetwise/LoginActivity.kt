package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetwise.databinding.ActivityLoginBinding
import com.example.budgetwise.databinding.ActivityRegisterBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var userDatabase: UserDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)
        userDatabase = UserDatabase(this)

        activityLoginBinding.LoginButton.setOnClickListener {
            val email = activityLoginBinding.EmailLoginEditText.text.toString().trim()
            val password = activityLoginBinding.PasswordLogin.text.toString().trim()

            //checking if the user exists
            val doesUserExist = userDatabase.getUser(email,password)
            if(doesUserExist){
                Toast.makeText(this,"Hello $email",Toast.LENGTH_SHORT).show()
                val mainActivityIntent = Intent(this,MainActivity::class.java)
                startActivity(mainActivityIntent)
                finish()
            }
            else{
                Toast.makeText(this,"Sorry Credentials are Incorrect",Toast.LENGTH_SHORT).show()
            }
        }

        activityLoginBinding.goToRegisterActivity.setOnClickListener {
            val goToRegisterActivity = Intent(this,RegisterActivity::class.java)
            startActivity(goToRegisterActivity)
        }

    }
}