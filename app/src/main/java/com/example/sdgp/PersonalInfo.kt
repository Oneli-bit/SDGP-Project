package com.example.sdgp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.List

class PersonalInfo : AppCompatActivity() {

    var user = User()
    var databaseReference: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var email : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        val continueNext = findViewById<Button>(R.id.continueNext)
        val fName = findViewById<EditText>(R.id.fName)
        val surname = findViewById<EditText>(R.id.lName)
        val male = findViewById<CheckBox>(R.id.checkboxMale)
        val female = findViewById<CheckBox>(R.id.checkboxFemale)
        val dob = findViewById<EditText>(R.id.user_dob)

        email = intent.getStringExtra("email")

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
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
            val db = PersonalInfoDB(this)

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
                    firebaseDatabase = FirebaseDatabase.getInstance()

                    databaseReference = firebaseDatabase!!.getReference("UserDetails")

                    // creating a variable for our object class
                    user= User()

                    if(male.isChecked) {
                        /*val add = db.addDetails("email",name,lName,"male",DOB)
                        if (add) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                            val calBFP = Intent(this, BFPMales1::class.java)
                            startActivity(calBFP)
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }*/
                        addUser(name,lName,"male",DOB)

                        val calBFP = Intent(this, BFPMales1::class.java)
                        startActivity(calBFP)
                    }
                    if (female.isChecked){
                        /*val add = db.addDetails("email",name,lName,"female",DOB)
                        if (add) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                            val showBFP = Intent (this,BFPPercentage::class.java)
                            startActivity(showBFP)
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }*/
                        addUser(name,lName,"female",DOB)

                        val calBFP = Intent(this, BFPFemales1::class.java)
                        startActivity(calBFP)
                    }
                }
            }
        }
    }

    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val act = Intent(this, activity::class.java)
        startActivity(act)

    }

    private fun addUser(fName:String,lName:String,gender:String,dob:String){
        user.setfName(fName)
        user.setlName(lName)
        user.gender = gender
        user.dob = dob
        user.email = email

        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                //databaseReference!!.setValue(measurements)
                databaseReference!!.child(fName+lName).setValue(user)
                // after adding this data we are showing toast message.
                Toast.makeText(applicationContext, "Data added to firebase", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(applicationContext, "Fail to add data $error", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }
    // get users data
    /*val List = db.getAllData("email")
    if (List.size>0) {
        val name:String = List[1] as String   // get data and show it
        fName.setText(name)
    }*/
}