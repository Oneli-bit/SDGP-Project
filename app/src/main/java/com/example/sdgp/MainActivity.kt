package com.example.sdgp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerButton = findViewById<Button>(R.id.regbtn)
        registerButton.setOnClickListener {
             val intent = Intent(this, Register::class.java)
             startActivity(intent)

        }

        val signButton = findViewById<Button>(R.id.signbtn)
        signButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }



        /*val info = Intent(this, PersonalInfo::class.java)
        startActivity(info)*/

    }
}