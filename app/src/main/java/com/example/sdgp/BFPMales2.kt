package com.example.sdgp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import kotlin.math.roundToInt


class BFPMales2 : AppCompatActivity() {

    var measurements = MaleMeasurements()
    var databaseReference: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference1: DatabaseReference? = null
    var firebaseDatabase1: FirebaseDatabase? = null
    private lateinit var interpreter: Interpreter
    lateinit var textView: TextView
    var bfp : Float = 0.0f
    private lateinit var userEmail : String
    private lateinit var fName: String
    private lateinit var lName: String
    private lateinit var gender: String
    //private var bfpArrayList : ArrayList<Float> = ArrayList(5)
    var array : DoubleArray = DoubleArray(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bfpmales2)

        val calculate = findViewById<Button>(R.id.calBFPMale)
        textView = findViewById(R.id.output)

        val intent1 = intent
        userEmail = intent1.getStringExtra("email").toString()
        fName = intent1.getStringExtra("fName").toString()
        lName = intent1.getStringExtra("lName").toString()
        gender = intent1.getStringExtra("gender").toString()

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigation.setOnItemSelectedListener {
            when (it.itemId) {        // add correct activities
                R.id.calBFP -> {
                    val intent2 = Intent(applicationContext, BFPFemales1::class.java)
                    intent2.putExtra("gender", gender)
                    intent2.putExtra("email", userEmail)
                    intent2.putExtra("fName", fName)
                    intent2.putExtra("lName", lName)
                    startActivity(intent2)
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

        try {
            interpreter = Interpreter(loadModelFile(),null)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        calculate.setOnClickListener {
            val intent = intent
            // receive the value by getStringExtra() method and
            // key must be same which is send by first activity
            var recAge: String? = intent.getStringExtra("age")
            var recWeight: String? = intent.getStringExtra("weight")
            var recHeight : String?= intent.getStringExtra("height")
            var recNeck: String? = intent.getStringExtra("neck")
            var recChest: String? = intent.getStringExtra("chest")
            var recAbdomen: String? = intent.getStringExtra("abdomen2")
            var recHip: String? = intent.getStringExtra("hip")


            recAge = recAge.toString()
            recWeight = recWeight.toString()
            recHeight = recHeight.toString()
            recNeck = recNeck.toString()
            recChest = recChest.toString()
            recAbdomen = recAbdomen.toString()
            recHip = recHip.toString()

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

                addMeasurements(recAge,recWeight,recHeight,recNeck,recChest,recAbdomen,recHip,thigh, knee, ankle, biceps, forearm, wrist)
                val array = floatArrayOf(recAge.toFloat(),recWeight.toFloat(),recHeight.toFloat(),recNeck.toFloat(),recChest.toFloat(),recAbdomen.toFloat(),
                    recHip.toFloat(),thigh.toFloat(),knee.toFloat(),ankle.toFloat(),biceps.toFloat(),forearm.toFloat(),wrist.toFloat())
                bfp = doInference(array)

                val round : Double = (bfp*100.0).roundToInt()/100.0
                updateBfp(round)

                val showBFP = Intent(this, BFPPercentage::class.java)
                showBFP.putExtra("bfp",bfp)
                showBFP.putExtra("email",userEmail)
                showBFP.putExtra("fName",fName)
                showBFP.putExtra("lName",lName)
                showBFP.putExtra("gender",gender)
                startActivity(showBFP)
            }

        }
    }

    private fun addMeasurements(age : String,  weight :String, height : String, neck : String, chest : String, abdomen : String,
                                hip :String, thigh : String, knee : String, ankle : String, biceps : String, forearm : String, wrist : String){

        measurements.email = userEmail
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
                databaseReference!!.child(fName+lName).setValue(measurements)
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

    private fun updateBfp(bfp:Double){

        firebaseDatabase1 = FirebaseDatabase.getInstance()
        databaseReference1 = firebaseDatabase1!!.getReference("BFP Percentage")
        val valuesRef = databaseReference1?.child(fName+lName)
        // Check if there are already five values
        valuesRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.childrenCount == 5L) {
                    // Five values exist, do nothing
                    Log.d(TAG, "Five values already exist, nothing to do")
                } else {
                    // Less than five values exist, find next available position
                    var nextPosition = 0
                    for (i in 0 until 5) {
                        val childSnapshot = snapshot.child(i.toString())
                        if (childSnapshot.exists() && childSnapshot.value != null) {
                            nextPosition = i + 1
                        } else {
                            break
                        }
                    }
                    // Set new value to next available position
                    if (nextPosition < 5) {
                        val newValue = bfp.toString()
                        valuesRef.child("email").setValue(userEmail)
                        valuesRef.child(nextPosition.toString()).setValue(newValue)
                            .addOnSuccessListener {
                                Log.d(TAG, "Value set in position $nextPosition")
                            }?.addOnFailureListener {
                                Log.e(TAG, "Error setting value in position $nextPosition: ${it.message}")
                            }
                    } else {
                        Log.d(TAG, "No available position to set new value")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error checking values: ${error.message}")
            }
        })
    }


    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val calBFP = Intent(this, activity::class.java)
        startActivity(calBFP)
    }

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val assetFileDescriptor = this.assets.openFd("NNlite_model_New.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val len = assetFileDescriptor.length
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, len)
    }

    fun doInference(values : FloatArray): Float {
        val input = values // 13
        //input[0] = values[0]
        val output = Array(1) {
            FloatArray(
                1
            )
        }

        interpreter.run(input, output)

        return output[0][0]


    }

}