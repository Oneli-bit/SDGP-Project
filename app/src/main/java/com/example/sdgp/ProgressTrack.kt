package com.example.sdgp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*


class ProgressTrack : AppCompatActivity() {

    lateinit var barchart : BarChart
    var databaseReference: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? = null
    private lateinit var userEmail: String
    private lateinit var fName: String
    private lateinit var lName: String
    private lateinit var gender: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_track)

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        navigation.selectedItemId = R.id.progress;
        //barchart = findViewById(R.id.bar_chart)
        val intent = intent
        userEmail = intent.getStringExtra("email").toString()
        fName = intent.getStringExtra("fName").toString()
        lName = intent.getStringExtra("lName").toString()
        gender = intent.getStringExtra("gender").toString()

        navigation.setOnItemSelectedListener {
            when (it.itemId) {        // add correct activities
                 R.id.calBFP -> {
                     if (gender == "male"){
                         val intent1 = Intent(applicationContext, BFPMales1::class.java)
                         intent1.putExtra("gender", gender)
                         intent1.putExtra("email", userEmail)
                         intent1.putExtra("fName", fName)
                         intent1.putExtra("lName", lName)
                         startActivity(intent1)
                     }
                     if (gender == "female"){
                         val intent2 = Intent(applicationContext, BFPFemales1::class.java)
                         intent2.putExtra("gender", gender)
                         intent2.putExtra("email", userEmail)
                         intent2.putExtra("fName", fName)
                         intent2.putExtra("lName", lName)
                         startActivity(intent2)
                     }
                     true
                 }
                 R.id.workout_plans -> {  // need to change activity
                     //loadActivity(BFPFemales1())
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
        println(userEmail)
        getBFPData()


    }

    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val act = Intent(this, activity::class.java)
        startActivity(act)

    }

    private fun getBFPData() {
        val attempt1 = findViewById<TextView>(R.id.attempt1)
        val attempt2 = findViewById<TextView>(R.id.attempt2)
        val attempt3 = findViewById<TextView>(R.id.attempt3)
        val attempt4 = findViewById<TextView>(R.id.attempt4)
        val attempt5 = findViewById<TextView>(R.id.attempt5)

        firebaseDatabase = FirebaseDatabase.getInstance()
        // below line is used to get
        // reference for our database.
        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase!!.getReference("BFP Percentage")

        val query: Query = databaseReference!!.orderByChild("email").equalTo(userEmail)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val a1 = userSnapshot.child("0").getValue(String()::class.java)
                    val a2 = userSnapshot.child("1").getValue(String()::class.java)
                    val a3 = userSnapshot.child("2").getValue(String()::class.java)
                    val a4 = userSnapshot.child("3").getValue(String()::class.java)
                    val a5 = userSnapshot.child("4").getValue(String()::class.java)
                    println(userEmail)
                    println(a1)
                    println(a2)
                    if (a1 != null) {
                        attempt1.setText(a1.toString()+" %")
                    }
                    if (a2 != null) {
                        attempt2.setText(a2.toString()+" %")
                    }
                    if (a3 != null) {
                        attempt3.setText(a3.toString()+" %")
                    }
                    if (a4 != null) {
                        attempt4.setText(a4.toString()+" %")
                    }
                    if (a5 != null) {
                        attempt5.setText(a5.toString()+" %")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error retrieving user data: " + error.message)
            }
        })

    }


}
