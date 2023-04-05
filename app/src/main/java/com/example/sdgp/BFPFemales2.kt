package com.example.sdgp

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import kotlin.math.roundToInt

class BFPFemales2 : AppCompatActivity() {

    var measurements = FemaleMeasurements()
    var databaseReference: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference1: DatabaseReference? = null
    var firebaseDatabase1: FirebaseDatabase? = null
    private lateinit var interpreter: Interpreter
    lateinit var textView: TextView
    private lateinit var userEmail: String
    private lateinit var fName: String
    private lateinit var lName: String
    private lateinit var gender: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bfpfemales2)

        val calculate = findViewById<Button>(R.id.calBFPFemale)
        textView = findViewById(R.id.output)

        val intent1=intent
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

            var recAge: String? = intent.getStringExtra("age")
            var recWeight: String? = intent.getStringExtra("weight")
            var recHeight: String? = intent.getStringExtra("height")
            var recNeck: String? = intent.getStringExtra("neck")
            var recChest: String? = intent.getStringExtra("chest")
            var recCalf: String? = intent.getStringExtra("calf")
            var recBiceps: String? = intent.getStringExtra("biceps")
            var recHip: String? = intent.getStringExtra("hip")
            var recWaist: String? = intent.getStringExtra("waist")

            recAge = recAge.toString()
            recWeight = recWeight.toString()
            recHeight = recHeight.toString()
            recNeck = recNeck.toString()
            recChest = recChest.toString()
            recCalf = recCalf.toString()
            recBiceps = recBiceps.toString()
            recHip = recHip.toString()
            recWaist = recWaist.toString()

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

                addMeasurements(recAge,recWeight,recHeight,recNeck,recChest,recCalf,recBiceps,recHip,recWaist,forearmF,pThighF,mThighF,dThighF,wristF,kneeF,elbowF,ankleF)

                val array = floatArrayOf(recWeight.toFloat(),recHeight.toFloat(),recAge.toFloat(),recNeck.toFloat(),recChest.toFloat(),recCalf.toFloat(),
                   recBiceps.toFloat(),recHip.toFloat(),recWaist.toFloat(),forearmF.toFloat(),pThighF.toFloat(),mThighF.toFloat(),dThighF.toFloat(),wristF.toFloat(),
                    kneeF.toFloat(),elbowF.toFloat(),ankleF.toFloat())

                //println(array.contentToString())

                //val array = floatArrayOf(60.7824f,1.5748f,22f,31.2f,87.3f,33.2f,26.8f,94f,68f,23.2f,58.5f,50f,39f,15.8f,34.6f,23f,22f)
                val bfp = doInference(array)

                val round : Double = (bfp*100.0).roundToInt()/100.0
                updateBfp(round)

                val showBFP = Intent(this, BFPPercentage::class.java)
                showBFP.putExtra("bfp",bfp)
                showBFP.putExtra("email",userEmail)
                showBFP.putExtra("fName",fName)
                showBFP.putExtra("lName",lName)
                showBFP.putExtra("gender",gender)
                startActivity(showBFP)
                //textView.setText("Result - $f")
            }

        }
    }

    private fun addMeasurements(age : String?,  weight :String?, height : String?, neck : String?, chest : String?, calf : String?, biceps : String?,
                                hip :String?, waist : String? , forearm : String, pThigh : String,mThigh : String, dThigh : String,wrist : String,
                                knee : String, elbow : String , ankle : String ){
        measurements.email = userEmail
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

    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val calBFP = Intent(this, activity::class.java)
        startActivity(calBFP)
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
                    Log.d(ContentValues.TAG, "Five values already exist, nothing to do")
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
                                Log.d(ContentValues.TAG, "Value set in position $nextPosition")
                            }?.addOnFailureListener {
                                Log.e(ContentValues.TAG, "Error setting value in position $nextPosition: ${it.message}")
                            }
                    } else {
                        Log.d(ContentValues.TAG, "No available position to set new value")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Error checking values: ${error.message}")
            }
        })
    }


    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val assetFileDescriptor = this.assets.openFd("NNF2_model.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val len = assetFileDescriptor.length
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, len)
    }

    fun doInference(values : FloatArray): Float {
        val input = values // 17

        interpreter.allocateTensors()
        val inputTensor = interpreter.getInputTensor(0)
        val inputShape = inputTensor.shape()
        val currentDims = inputShape.copyOf()
// Set the new dimensions of the input tensor shape
        val newDims = intArrayOf(1, 17)

// Resize the input tensor shape
        for (i in currentDims.indices) {
            inputShape.set(i, newDims[i])
        }
        println(inputShape.contentToString())

        //input[0] = values[0]
        val output = Array(1) {
            FloatArray(
                1
            )
        }

        interpreter.run(input, output)
        //interpreter.runForMultipleInputsOutputs(arrayOf(values),output.mu)
        return output[0][0]
        //interpreter.run

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