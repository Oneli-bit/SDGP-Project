package com.example.sdgp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BFPMales1 : AppCompatActivity() {

    private lateinit var userEmail: String
    private lateinit var fName: String
    private lateinit var lName: String
    private lateinit var gender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bfpmales1)

        val intent = intent
        userEmail = intent.getStringExtra("email").toString()
        fName = intent.getStringExtra("fName").toString()
        lName = intent.getStringExtra("lName").toString()
        gender = intent.getStringExtra("gender").toString()

        println(userEmail)
        println(fName)
        println(lName)

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val continueNext = findViewById<Button>(R.id.nextBFPInputsM)
        val ageM = findViewById<EditText>(R.id.ageInputM)
        val weightM = findViewById<EditText>(R.id.weightInputM)
        val heightM = findViewById<EditText>(R.id.heightInputM)
        val neckM = findViewById<EditText>(R.id.neckInputM)
        val chestM = findViewById<EditText>(R.id.chestInputM)
        val abdomen2M = findViewById<EditText>(R.id.abdomen2InputM)
        val hipM = findViewById<EditText>(R.id.hipInputM)

        navigation.selectedItemId = R.id.calBFP

        navigation.setOnItemSelectedListener {
            when (it.itemId) {        // add correct activities
                R.id.calBFP -> {
                    val intent1 = Intent(applicationContext, BFPMales1::class.java)
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
                    act.putExtra("gender", gender)
                    startActivity(act)
                    true}
            }
        }

        continueNext.setOnClickListener {
            nextScreen(ageM,weightM,heightM,neckM,chestM,abdomen2M,hipM)
        }

    }

    private fun nextScreen(
        ageM: EditText,
        weightM: EditText,
        heightM: EditText,
        neckM: EditText,
        chestM: EditText,
        abdomen2M: EditText,
        hipM: EditText
    ) {
        val age = ageM.text.toString()
        val weight = weightM.text.toString()
        val height = heightM.text.toString()
        val neck = neckM.text.toString()
        val chest = chestM.text.toString()
        val abdomen2 = abdomen2M.text.toString()
        val hip = hipM.text.toString()

        if (age.isEmpty()||weight.isEmpty()||height.isEmpty()||neck.isEmpty()||chest.isEmpty()||abdomen2.isEmpty()||hip.isEmpty()){
            Toast.makeText(this,"Fill all the inputs..",
                Toast.LENGTH_SHORT).show()
        }
        else{
            val intAge = age.toInt()
            if (intAge<22 || intAge>74){
                Toast.makeText(this,"Select Age between 22 - 74.",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                // if (age in 22..74)
                val nextInputs = Intent(this, BFPMales2::class.java)
                nextInputs.putExtra("age", age)
                nextInputs.putExtra("weight", weight)
                nextInputs.putExtra("height", height)
                nextInputs.putExtra("neck", neck)
                nextInputs.putExtra("chest", chest)
                nextInputs.putExtra("abdomen2", abdomen2)
                nextInputs.putExtra("hip", hip)
                nextInputs.putExtra("email", userEmail)
                nextInputs.putExtra("fName",fName)
                nextInputs.putExtra("lName",lName)
                nextInputs.putExtra("gender",gender)
                startActivity(nextInputs)
            }
        }
    }

    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val calBFP = Intent(this, activity::class.java)
        startActivity(calBFP)
    }
}