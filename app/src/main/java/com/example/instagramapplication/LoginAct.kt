package com.example.instagramapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser


class LoginAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (ParseUser.getCurrentUser() != null){
            //goToMain()
        }

        findViewById<Button>(R.id.btn_Login).setOnClickListener{
            val username = findViewById<EditText>(R.id.et_Username).text.toString()
            val password = findViewById<EditText>(R.id.et_Password).text.toString()

            loginUser(username,password)
        }

        findViewById<Button>(R.id.btn_Register).setOnClickListener{
            val username = findViewById<EditText>(R.id.et_Username).text.toString()
            val password = findViewById<EditText>(R.id.et_Password).text.toString()

            signUpUser(username,password)
        }
    }

    private fun signUpUser(username: String, password: String){
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // Hooray! Let them use the app now.
                Toast.makeText(this, "Signed Up!", Toast.LENGTH_SHORT).show()
                loginUser(username,password)
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error signing up, try again!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(username: String, password: String){
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i("ROB", "User logged in")
                goToMain()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    private fun goToMain(){
        val intent = Intent(this@LoginAct, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}