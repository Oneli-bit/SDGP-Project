package com.example.sdgp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List

class PersonalInfo : AppCompatActivity() {

    var email : String? = null
    private lateinit var firstName: String
    private lateinit var lastName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        val continueNext = findViewById<Button>(R.id.continueNext)
        val fName = findViewById<EditText>(R.id.fName)
        val surname = findViewById<EditText>(R.id.lName)
        val male = findViewById<CheckBox>(R.id.checkboxMale)
        val female = findViewById<CheckBox>(R.id.checkboxFemale)
        val dob = findViewById<EditText>(R.id.user_dob)

        val intent = intent
        email = intent.getStringExtra("email")
        println(email)

        dob.setOnClickListener {
            // get the instance of calendar.
            val c = Calendar.getInstance()

            // get day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // creating a variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // setting date to the edit text.
                    val date = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                    dob.setText(date)
                },
                // passing year, month and day for the selected date in date picker.
                year,
                month,
                day
            )
            // display date picker dialog.
            datePickerDialog.show()
        }
        continueNext.setOnClickListener {

            val name = fName.text.toString()
            val lName = surname.text.toString()
            val DOB = dob.text.toString()

            if (name.isEmpty() || lName.isEmpty() || DOB.isEmpty() || (!male.isChecked && !female.isChecked)){
                Toast.makeText(this,"Please enter your details.",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                if (male.isChecked && female.isChecked) {
                    Toast.makeText(
                        this, "Select Male or Female.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if(male.isChecked) {
                        firstName=name
                        lastName=lName
                        addUser(name,lName,"male",DOB,email.toString())
                        val calBFP = Intent(this, BFPMales1::class.java)
                        calBFP.putExtra("email",email.toString())
                        calBFP.putExtra("fName",name)
                        calBFP.putExtra("lName",lName)
                        startActivity(calBFP)
                    }
                    if (female.isChecked){
                        firstName=name
                        lastName=lName
                        addUser(name,lName,"female",DOB,email.toString())
                        val calBFP = Intent(this, BFPFemales1::class.java)
                        calBFP.putExtra("email",email.toString())
                        calBFP.putExtra("fName",name)
                        calBFP.putExtra("lName",lName)
                        startActivity(calBFP)
                    }
                }
            }
        }
    }


    private fun addUser(fName:String,lName:String,gender:String,dob:String,email:String) {

        val database = Firebase.database
        val usersRef = database.reference.child("users")
        val userId = usersRef.push().key.toString() // generate a unique key for the user
        val userRef = usersRef.child("$fName $lName")
        userRef.child("firstName").setValue(fName)
        userRef.child("lastName").setValue(lName)
        userRef.child("email").setValue(email)
        userRef.child("userId").setValue(userId)
        userRef.child("gender").setValue(gender)
        userRef.child("dob").setValue(dob)
    }

}