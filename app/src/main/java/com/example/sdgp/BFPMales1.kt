package com.example.sdgp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class BFPMales1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bfpmales1)

        val continueNext = findViewById<Button>(R.id.nextBFPInputsM)
        val ageM = findViewById<EditText>(R.id.ageInputM)
        val weightM = findViewById<EditText>(R.id.weightInputM)
        val heightM = findViewById<EditText>(R.id.heightInputM)
        val neckM = findViewById<EditText>(R.id.neckInputM)
        val chestM = findViewById<EditText>(R.id.chestInputM)
        val abdomen2M = findViewById<EditText>(R.id.abdomen2InputM)
        val hipM = findViewById<EditText>(R.id.hipInputM)

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        navigation.selectedItemId = R.id.calBFP

        navigation.setOnItemSelectedListener {
            when (it.itemId) {        // add correct activities
                R.id.calBFP -> {
                    loadActivity(BFPMales1())
                    true
                }
                R.id.workout_plans -> {  // need to change activity
                    loadActivity(BFPFemales1())
                    true
                }
                R.id.progress -> {
                    loadActivity(ProgressTrack())
                    true
                }
                else -> { loadActivity(ProgressTrack())
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
                startActivity(nextInputs)
            }
        }
    }

    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val calBFP = Intent(this, activity::class.java)
        startActivity(calBFP)
    }
}