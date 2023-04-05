package com.example.sdgp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class BFPFemales1 : AppCompatActivity() {
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
            startActivity(nextInputs)
        }
    }
}