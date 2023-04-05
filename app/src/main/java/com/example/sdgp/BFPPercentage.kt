package com.example.sdgp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class BFPPercentage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bfppercentage)

        val test = findViewById<Button>(R.id.workout_plans)
        test.setOnClickListener {
            val intent = Intent(this,ProgressTrack::class.java)
            startActivity(intent)
        }
    }
}