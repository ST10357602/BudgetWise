package com.example.budgetwise

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetwise.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private lateinit var userDatabase: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)
        userDatabase = UserDatabase(this)

        activityRegisterBinding.RegisterButton.setOnClickListener {
            val username = activityRegisterBinding.EmailRegisterEditText.text.toString()
            val password = activityRegisterBinding.PasswordRegister.text.toString()
            val confirmPassword = activityRegisterBinding.ConfirmPasswordRegister.text.toString()

            if(password == confirmPassword && username != ""){
                if(username !=""){
                    val user = UserClass(0,username,password)
                    userDatabase.saveUser(user)
                    Toast.makeText(this,"Registration Successful",Toast.LENGTH_SHORT).show()
                    val goToLogin = Intent(this,LoginActivity::class.java)
                    startActivity(goToLogin)
                }
                else{
                    Toast.makeText(this,"Email field cannot be empty",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"Passwords Do Not Match",Toast.LENGTH_SHORT).show()
            }
        }

        activityRegisterBinding.goToLogin.setOnClickListener {
            val goToLogin = Intent(this,LoginActivity::class.java)
            startActivity(goToLogin)
        }
    }
}