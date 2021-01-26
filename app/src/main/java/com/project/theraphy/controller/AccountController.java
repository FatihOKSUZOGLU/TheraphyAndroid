package com.project.theraphy.controller;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.theraphy.R;
import com.project.theraphy.model.patient;
import com.project.theraphy.model.therapist;
import com.project.theraphy.view.EditProfile;

public class AccountController {
    TextView Name,Gender,Sessioncount, current_t, Email, Phone , Age , About;
    ImageView imbut;
    FirebaseFirestore FFBdatabase;
    FirebaseAuth mAuth;
    ImageView edit;
    private StorageReference SR;
    private FirebaseStorage FS;

    public AccountController(View v){
        Name = v.findViewById(R.id.patient_name);
        Gender = v.findViewById(R.id.patient_gender);
        Sessioncount = v.findViewById(R.id.patient_theraphy);
        current_t = v.findViewById(R.id.patient_current_t);
        Email = v.findViewById(R.id.patient_email);
        Phone = v.findViewById(R.id.patient_phone);
        Age = v.findViewById(R.id.patient_age);
        About = v.findViewById(R.id.patient_about);
        imbut = v.findViewById(R.id.therapist_list_image);
        FS = FirebaseStorage.getInstance();
        SR = FS.getReference();
        mAuth = FirebaseAuth.getInstance();
        edit = v.findViewById(R.id.EditProfileButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfile.class);
                v.getContext().startActivity(intent);
            }
        });
        FFBdatabase = FirebaseFirestore.getInstance();
        FFBdatabase.collection("Patients").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    patient p =  document.toObject(patient.class);
                    Name.setText(p.getName());
                    Gender.setText(p.getGender());
                    try {
                        FFBdatabase.collection("Therapists").document(p.getCurrent_t()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document1 = task.getResult();
                                therapist t = document1.toObject(therapist.class);
                                current_t.setText(t.getName());
                            }
                        });
                    }catch (Exception e){
                        current_t.setText("No Therapist.");
                    }
                    Email.setText(p.getName());
                    Phone.setText(p.getPhone());
                    Sessioncount.setText(Integer.toString(p.getChat_sessions_count()));
                    Age.setText(Integer.toString(p.getAge()));
                    StorageReference SRchild= SR.child("images/" + mAuth.getCurrentUser().getUid());
                    SRchild.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            Glide.with(v.getContext()).load(imageURL).into(imbut);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                    About.setText(p.getAbout());
                }else{
                    Toast.makeText(v.getContext(),"Wrong Database argument!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }



}
