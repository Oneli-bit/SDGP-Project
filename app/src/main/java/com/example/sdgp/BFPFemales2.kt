package com.example.sdgp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class BFPFemales2 : AppCompatActivity() {

    var measurements = FemaleMeasurements()
    var databaseReference: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bfpfemales2)

        val calculate = findViewById<Button>(R.id.calBFPFemale)

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

            val recAge: String? = intent.getStringExtra("age")
            val recWeight: String? = intent.getStringExtra("weight")
            val recHeight: String? = intent.getStringExtra("height")
            val recNeck: String? = intent.getStringExtra("neck")
            val recChest: String? = intent.getStringExtra("chest")
            val recCalf: String? = intent.getStringExtra("calf")
            val recBiceps: String? = intent.getStringExtra("biceps")
            val recHip: String? = intent.getStringExtra("hip")
            val recWaist: String? = intent.getStringExtra("waist")

            val forearm = findViewById<EditText>(R.id.forearmInputF)
            val pThigh = findViewById<EditText>(R.id.proximalThighInputF)
            val mThigh = findViewById<EditText>(R.id.middleThighInputF)
            val dThigh = findViewById<EditText>(R.id.distalThighInputF)
            val wrist = findViewById<EditText>(R.id.wristInputF)
            val knee = findViewById<EditText>(R.id.kneeInputF)
            val elbow = findViewById<EditText>(R.id.elbowInputF)
            val ankle = findViewById<EditText>(R.id.ankleInputF)

            val forearmF = forearm.text.toString()
            val pThighF = pThigh.text.toString()
            val mThighF = mThigh.text.toString()
            val dThighF = dThigh.text.toString()
            val wristF = wrist.text.toString()
            val kneeF = knee.text.toString()
            val elbowF = elbow.text.toString()
            val ankleF = ankle.text.toString()

            if (forearm.toString().isEmpty() || pThigh.toString().isEmpty() || mThigh.toString().isEmpty() || dThigh.toString().isEmpty() || wrist.toString().isEmpty()
                || knee.toString().isEmpty() || elbow.toString().isEmpty() || ankle.toString().isEmpty()){
                Toast.makeText(this,"Fill all the inputs..",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                firebaseDatabase = FirebaseDatabase.getInstance()

                databaseReference = firebaseDatabase!!.getReference("FemaleMeasurements")

                // creating a variable for our object class
                measurements = FemaleMeasurements()

                addMeasurements("email",recAge,recWeight,recHeight,recNeck,recChest,recCalf,recBiceps,recHip,recWaist,forearmF,pThighF,mThighF,dThighF,wristF,kneeF,elbowF,ankleF)
            }

        }
    }

    private fun addMeasurements(email: String, age : String?,  weight :String?, height : String?, neck : String?, chest : String?, calf : String?, biceps : String?,
                                hip :String?, waist : String? , forearm : String, pThigh : String,mThigh : String, dThigh : String,wrist : String,
                                knee : String, elbow : String , ankle : String ){
        measurements.age = age
        measurements.weight = weight
        measurements.height = height
        measurements.neck = neck
        measurements.chest = chest
        measurements.calf = calf
        measurements.biceps = biceps
        measurements.hip = hip
        measurements.waist = waist
        measurements.forearm = forearm
        measurements.setpThigh(pThigh)
        measurements.setmThigh(mThigh)
        measurements.setdThigh(dThigh)
        measurements.wrist = wrist
        measurements.knee = knee
        measurements.elbow = elbow
        measurements.ankle = ankle

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

    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val calBFP = Intent(this, activity::class.java)
        startActivity(calBFP)
    }

}


/*val db = FemaleDatabase(this)
                val add = db.addMeasurements("email",recAge,recWeight,recHeight,recNeck,recChest,recCalf,recBiceps,recHip,recWaist,forearmF,pThighF,mThighF,dThighF,wristF,kneeF,elbowF,ankleF)
                if (add) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    val cal = Intent (this,BFPPercentage::class.java)
                    startActivity(cal)
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }*/