package com.example.machinetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.machinetest.databinding.ActivityLoginBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
   private lateinit var databaseRef : DatabaseReference
    private val binding : ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val username = binding.usernamelogin.text.toString()
            val password = binding.passwordlogin.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
            readData(username)

           } else {

                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()

            }
        }
    }
            private fun readData(username: String) {
                //get reference upto Users
                databaseRef = FirebaseDatabase.getInstance().getReference("Users")
                databaseRef.child(username).get().addOnSuccessListener {

                    // check if user exist or not
                    if (it.exists()) {
                        // reading data from firebase
                        val username = it.child("username").value
                        val password = it.child("password").value
                        val intent = Intent(this, HomepageActivity::class.java)
                        binding.usernamelogin.text.clear()
                        binding.passwordlogin.text.clear()
                        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()
                        startActivity(intent)

                    } else {
                        //will show user that he is not exist into database
                        Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,SignUpActivity::class.java)
                        startActivity(intent)

                    }

                }.addOnFailureListener {
                    //to show toast to user if app causing problem to connect with firebase
                    Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show()

                }
            }}