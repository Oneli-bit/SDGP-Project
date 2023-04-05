package com.example.sdgp

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class ViewProfile : AppCompatActivity() {
    var databaseReference: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? = null
    private lateinit var userEmail: String
    private lateinit var recgender: String
    private lateinit var fName1: String
    private lateinit var lName1: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        val fName = findViewById<TextView>(R.id.fName)
        val lName = findViewById<TextView>(R.id.lName)
        val gender = findViewById<TextView>(R.id.gender)
        val dob = findViewById<TextView>(R.id.dob)
        val mail = findViewById<TextView>(R.id.email)
        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val intent = intent
        userEmail = intent.getStringExtra("email").toString()
        recgender = intent.getStringExtra("gender").toString()
        fName1 = intent.getStringExtra("fName").toString()
        lName1 = intent.getStringExtra("lName").toString()

        /*if (recgender.equals("male")){
            profile.setBackgroundResource(R.color.blue)
            navigation.setBackgroundResource(R.color.nav)
        }
        if (recgender.equals("female")){
            profile.setBackgroundResource(R.color.pink)
            navigation.setBackgroundResource(R.color.nav_female)
        }*/

        navigation.selectedItemId = R.id.profile;

        navigation.setOnItemSelectedListener {
            when (it.itemId) {        // add correct activities
                R.id.calBFP -> {
                    if (recgender == "male"){
                        val intent1 = Intent(applicationContext, BFPMales1::class.java)
                        intent1.putExtra("gender", recgender)
                        intent1.putExtra("email", userEmail)
                        intent1.putExtra("fName", fName1)
                        intent1.putExtra("lName", lName1)
                        startActivity(intent1)
                    }
                    else{
                        val intent2 = Intent(applicationContext, BFPFemales1::class.java)
                        intent2.putExtra("gender", recgender)
                        intent2.putExtra("email", userEmail)
                        intent2.putExtra("fName", fName1)
                        intent2.putExtra("lName", lName1)
                        startActivity(intent2)
                    }
                    true
                }
                R.id.workout_plans -> {  // need to change activity
                    loadActivity(BFPFemales1())
                    true
                }
                R.id.progress -> {
                    val act = Intent(this, ProgressTrack::class.java)
                    act.putExtra("email",userEmail)
                    act.putExtra("fName",fName1)
                    act.putExtra("lName",lName1)
                    act.putExtra("gender",recgender)
                    startActivity(act)
                    true
                }
                else -> {  val act = Intent(this, ViewProfile::class.java)
                    act.putExtra("email",userEmail)
                    act.putExtra("fName",fName1)
                    act.putExtra("lName",lName1)
                    act.putExtra("gender", recgender)
                    startActivity(act)
                    true}
            }
        }



        firebaseDatabase = FirebaseDatabase.getInstance()
        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase!!.getReference("users")

        val query: Query = databaseReference!!.orderByChild("email").equalTo(userEmail)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val fName1 = userSnapshot.child("firstName").getValue(String::class.java)
                    val lName1 = userSnapshot.child("lastName").getValue(String::class.java)
                    val email1 = userSnapshot.child("email").getValue(String::class.java)
                    val gender1 = userSnapshot.child("gender").getValue(String::class.java)
                    val dob1 = userSnapshot.child("dob").getValue(String::class.java)

                    println(email1)
                    println(fName1)
                    println(lName1)

                    fName.setText(fName1)
                    lName.setText(lName1)
                    gender.setText(gender1)
                    dob.setText(dob1)
                    mail.setText(email1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Error retrieving user data: " + error.message)
            }
        })

    }

    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val act = Intent(this, activity::class.java)
        startActivity(act)

    }
}