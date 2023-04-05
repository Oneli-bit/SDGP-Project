package com.example.sdgp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class Login extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth=FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.registerNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressBar.setVisibility(view.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.contains("@gmail.com")){
                    Toast.makeText(Login.this, "Enter a valid email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length()<6){
                    Toast.makeText(Login.this, "Password must contain 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                getData(email);

                            } else {
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

    }

    private void getData(String uEmail) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference("users");

        Query query = databaseReference.orderByChild("email").equalTo(uEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String fName = userSnapshot.child("firstName").getValue(String.class);
                    String lName = userSnapshot.child("lastName").getValue(String.class);
                    String email = userSnapshot.child("email").getValue(String.class);
                    String gender = userSnapshot.child("gender").getValue(String.class);

                    assert gender != null;
                    if (gender.equals("male")){
                        Intent intent=new Intent(getApplicationContext(),BFPMales1.class);
                        intent.putExtra("gender",gender);
                        intent.putExtra("email",email);
                        intent.putExtra("fName",fName);
                        intent.putExtra("lName",lName);
                        startActivity(intent);
                    }
                    if (gender.equals("female")){
                        Intent intent=new Intent(getApplicationContext(),BFPFemales1.class);
                        intent.putExtra("gender",gender);
                        intent.putExtra("email",email);
                        intent.putExtra("fName",fName);
                        intent.putExtra("lName",lName);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error retrieving user data: " + error.getMessage());
            }
        });
    }
}