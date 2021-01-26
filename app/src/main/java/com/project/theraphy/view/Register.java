package com.project.theraphy.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.theraphy.R;
import com.project.theraphy.model.patient;
import com.project.theraphy.model.request;
import com.project.theraphy.model.test_to;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailText , passText , name , phone , gender ,age, body;
    FirebaseFirestore firebaseFirestoreDb;
    CollectionReference collectionReference;
    test_to resulted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        try {
            resulted = (test_to) getIntent().getSerializableExtra("result");
        }catch (Exception e){

        }
        emailText = findViewById(R.id.edit_email);
        passText = findViewById(R.id.edit_pass);
        name = findViewById(R.id.edit_name);
        phone = findViewById(R.id.edit_phone);
        gender = findViewById(R.id.edit_gender);
        age = findViewById(R.id.edit_age);
        body = findViewById(R.id.editTextbody);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
    }

    public void Register(View view){
        patient p = new patient(name.getText().toString(),
                Integer.parseInt(age.getText().toString()),
                emailText.getText().toString(),
                phone.getText().toString(),
                passText.getText().toString(),
                gender.getText().toString(),
                "",
                "",
                "",
                new ArrayList<request>(),
                0,
                "",
                body.getText().toString());

        mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            p.setId(mAuth.getCurrentUser().getUid());
                            firebaseFirestoreDb.collection("Patients").document(mAuth.getCurrentUser().getUid()).set(p).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    resulted.setTest_pid(mAuth.getCurrentUser().getUid());
                                    firebaseFirestoreDb.collection("Test_to").document(mAuth.getCurrentUser().getUid()).set(resulted);
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Cloud Firestore Database Error!!!", Toast.LENGTH_LONG).show();

                                }
                            });

                        } else {
                            Toast.makeText(Register.this, "Register failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    public void Login(View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}