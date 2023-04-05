package com.example.sdgp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BFPFemales1 : AppCompatActivity() {

    private lateinit var userEmail: String
    private lateinit var fName: String
    private lateinit var lName: String
    private lateinit var gender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bfpfemales1)
        val continueNext = findViewById<Button>(R.id.nextBFPInputsF)
        val age = findViewById<EditText>(R.id.ageInputF)
        val weight = findViewById<EditText>(R.id.weightInputF)
        val height = findViewById<EditText>(R.id.heightInputF)
        val neck = findViewById<EditText>(R.id.neckInputF)
        val chest = findViewById<EditText>(R.id.chestInputF)
        val calf = findViewById<EditText>(R.id.calfInputF)
        val biceps = findViewById<EditText>(R.id.bicepsInputF)
        val hips = findViewById<EditText>(R.id.hipsInputF)
        val waist = findViewById<EditText>(R.id.waistInputF)

        val intent = intent
        userEmail = intent.getStringExtra("email").toString()
        fName = intent.getStringExtra("fName").toString()
        lName = intent.getStringExtra("lName").toString()
        gender = intent.getStringExtra("gender").toString()

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigation.selectedItemId = R.id.calBFP
        navigation.setOnItemSelectedListener {
            when (it.itemId) {        // add correct activities
                R.id.calBFP -> {
                    val intent1 = Intent(applicationContext, BFPFemales1::class.java)
                    intent1.putExtra("gender", gender)
                    intent1.putExtra("email", userEmail)
                    intent1.putExtra("fName", fName)
                    intent1.putExtra("lName", lName)
                    startActivity(intent1)
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

        continueNext.setOnClickListener {
            nextScreen(age,weight,height,neck,chest,calf,biceps,hips,waist)
        }
    }

    private fun nextScreen(
        ageF: EditText,
        weightF: EditText,
        heightF: EditText,
        neckF: EditText,
        chestF: EditText,
        calfF : EditText,
        bicepsF: EditText,
        hipsF: EditText,
        waistF: EditText
    ){
        val age = ageF.text.toString()
        val weight = weightF.text.toString()
        val height = heightF.text.toString()
        val neck = neckF.text.toString()
        val chest = chestF.text.toString()
        val calf = calfF.text.toString()
        val biceps = bicepsF.text.toString()
        val hip = hipsF.text.toString()
        val waist = waistF.text.toString()

        if (age.isEmpty() || weight.isEmpty() || height.isEmpty() || neck.isEmpty() || chest.isEmpty() || calf.isEmpty() ||biceps.isEmpty() || hip.isEmpty() || waist.isEmpty() ){
            Toast.makeText(this,"Fill all the inputs..",
                Toast.LENGTH_SHORT).show()
        }
        else{
            val nextInputs = Intent(this, BFPFemales2::class.java)
            nextInputs.putExtra("age", age)
            nextInputs.putExtra("weight", weight)
            nextInputs.putExtra("height", height)
            nextInputs.putExtra("neck", neck)
            nextInputs.putExtra("chest", chest)
            nextInputs.putExtra("calf", calf)
            nextInputs.putExtra("biceps", biceps)
            nextInputs.putExtra("hip", hip)
            nextInputs.putExtra("waist", waist)
            nextInputs.putExtra("email", userEmail)
            nextInputs.putExtra("fName",fName)
            nextInputs.putExtra("lName",lName)
            nextInputs.putExtra("gender",gender)
            startActivity(nextInputs)
        }
    }

    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val calBFP = Intent(this, activity::class.java)
        startActivity(calBFP)
    }
}