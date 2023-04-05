package com.example.sdgp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.roundToInt

class BFPPercentage : AppCompatActivity() {

    private lateinit var userEmail: String
    private lateinit var fName: String
    private lateinit var lName: String
    private lateinit var gender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bfppercentage)

        val bfp = findViewById<TextView>(R.id.bfpPerc)

        val intent = intent
        val percentage : Float = intent.getFloatExtra("bfp",0f)
        userEmail = intent.getStringExtra("email").toString()
        fName = intent.getStringExtra("fName").toString()
        lName = intent.getStringExtra("lName").toString()
        gender = intent.getStringExtra("gender").toString()

        val round : Double = (percentage*100.0).roundToInt()/100.0
        bfp.setText(round.toString()+"\n"+"%")

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigation.setOnItemSelectedListener {
            when (it.itemId) {        // add correct activities
                R.id.calBFP -> {
                    if (gender == "male"){
                        val intent1 = Intent(applicationContext, BFPMales1::class.java)
                        intent1.putExtra("gender", gender)
                        intent1.putExtra("email", userEmail)
                        intent1.putExtra("fName", fName)
                        intent1.putExtra("lName", lName)
                        startActivity(intent1)
                    }
                    else{
                        val intent2 = Intent(applicationContext, BFPFemales1::class.java)
                        intent2.putExtra("gender", gender)
                        intent2.putExtra("email", userEmail)
                        intent2.putExtra("fName", fName)
                        intent2.putExtra("lName", lName)
                        startActivity(intent2)
                    }
                    true
                }
                R.id.workout_plans -> {  // need to change activity
                    loadActivity(BFPFemales1())
                    true
                }
                R.id.progress -> {
                    val act = Intent(this, ProgressTrack::class.java)
                    act.putExtra("email",userEmail)
                    act.putExtra("fName",fName)
                    act.putExtra("lName",lName)
                    act.putExtra("gender",gender)
                    startActivity(act)
                    true
                }
                else -> {
                    val act = Intent(this, ViewProfile::class.java)
                    act.putExtra("email",userEmail)
                    act.putExtra("fName",fName)
                    act.putExtra("lName",lName)
                    act.putExtra("gender",gender)
                    startActivity(act)
                    true}
            }
        }

        val test = findViewById<Button>(R.id.workout_plans)
        test.setOnClickListener {
            val act = Intent(this, ProgressTrack::class.java)
            act.putExtra("email",userEmail)
            act.putExtra("fName",fName)
            act.putExtra("lName",lName)
            act.putExtra("gender",gender)
            startActivity(act)
        }
    }
    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val calBFP = Intent(this, activity::class.java)
        startActivity(calBFP)
    }
}