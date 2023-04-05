package com.example.sdgp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*


class BFPMales2 : AppCompatActivity() {

    var measurements = MaleMeasurements()
    var databaseReference: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bfpmales2)

        val calculate = findViewById<Button>(R.id.calBFPMale)

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

        calculate.setOnClickListener {
            val intent = intent
            // receive the value by getStringExtra() method and
            // key must be same which is send by first activity
            val recAge: String? = intent.getStringExtra("age")
            val recWeight: String? = intent.getStringExtra("weight")
            val recHeight : String?= intent.getStringExtra("height")
            val recNeck: String? = intent.getStringExtra("neck")
            val recChest: String? = intent.getStringExtra("chest")
            val recAbdomen: String? = intent.getStringExtra("abdomen2")
            val recHip: String? = intent.getStringExtra("hip")

            val thighM = findViewById<EditText>(R.id.thighInput)
            val kneeM = findViewById<EditText>(R.id.kneeInput)
            val ankleM = findViewById<EditText>(R.id.ankleInput)
            val bicepsM = findViewById<EditText>(R.id.bicepsInput)
            val forearmM = findViewById<EditText>(R.id.forearmInput)
            val wristM = findViewById<EditText>(R.id.wristInput)

            val thigh = thighM.text.toString()
            val knee = kneeM.text.toString()
            val ankle = ankleM.text.toString()
            val biceps = bicepsM.text.toString()
            val forearm = forearmM.text.toString()
            val wrist = wristM.text.toString()

            if (thigh.isEmpty()||knee.isEmpty()||ankle.isEmpty()||biceps.isEmpty()||forearm.isEmpty()||wrist.isEmpty()){
                Toast.makeText(this,"Fill all the inputs..",
                    Toast.LENGTH_SHORT).show()
            }

            else{

                firebaseDatabase = FirebaseDatabase.getInstance()

                databaseReference = firebaseDatabase!!.getReference("MaleMeasurements")

                // creating a variable for our object class
                measurements = MaleMeasurements()

                if (recAge != null) {
                    if (recWeight != null) {
                        if (recHeight != null) {
                            if (recNeck != null) {
                                if (recChest != null) {
                                    if (recAbdomen != null) {
                                        if (recHip != null) {
                                            addMeasurements("email4",recAge,recWeight,recHeight,recNeck,recChest,recAbdomen,recHip,thigh, knee, ankle, biceps, forearm, wrist)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addMeasurements(email: String, age : String,  weight :String, height : String, neck : String, chest : String, abdomen : String,
                                hip :String, thigh : String, knee : String, ankle : String, biceps : String, forearm : String, wrist : String){

       // measurements.email = email
        measurements.age = age
        measurements.weight = weight
        measurements.height = height
        measurements.neck = neck
        measurements.chest = chest
        measurements.abdomen = abdomen
        measurements.hip = hip
        measurements.thigh = thigh
        measurements.knee = knee
        measurements.ankle = ankle
        measurements.biceps = biceps
        measurements.forearm = forearm
        measurements.wrist = wrist

        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                //databaseReference!!.setValue(measurements)
                databaseReference!!.child(email).setValue(measurements)
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

    private fun getData(){

    }

    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val calBFP = Intent(this, activity::class.java)
        startActivity(calBFP)
    }

    // update part
    /*val db = MyDatabase(this)
    val update = db.updateMeasurements("email",recAge,recWeight,recHeight,recNeck,recChest,recAbdomen,recHip,thigh,knee,ankle,biceps,forearm,wrist)
    if (update) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }*/

    /*val list = db.checkEmail("email")
                if (list){
                    val update = db.updateMeasurements("email1",recAge,recWeight,recHeight,recNeck,recChest,recAbdomen,recHip,thighM.text.toString(),kneeM.text.toString(),ankleM.text.toString(),bicepsM.text.toString(),forearmM.text.toString(),wristM.text.toString())
                    if (update) {
                        Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }*/

    /*val db = MyDatabase(this)
                val add = db.addMeasurements("email1",recAge,recWeight,recHeight,recNeck,recChest,recAbdomen,recHip,thighM.text.toString(),kneeM.text.toString(),ankleM.text.toString(),bicepsM.text.toString(),forearmM.text.toString(),wristM.text.toString())
                if (add) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    val showBFP = Intent (this,BFPPercentage::class.java)
                    startActivity(showBFP)
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }*/


}