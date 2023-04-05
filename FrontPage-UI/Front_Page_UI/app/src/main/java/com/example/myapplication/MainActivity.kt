package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerButton = findViewById<Button>(R.id.regbtn)
        registerButton.setOnClickListener{
            val intent = Intent(this,registration::class.java)
            startActivity(intent)

        }

        val signButton = findViewById<Button>(R.id.signbtn)
        signButton.setOnClickListener{
            val intent = Intent(this,logIn::class.java)
            startActivity(intent)
    }



}