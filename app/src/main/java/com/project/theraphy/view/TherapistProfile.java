package com.project.theraphy.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.theraphy.R;
import com.project.theraphy.fragment.TherapistDetail;
import com.project.theraphy.model.patient;
import com.project.theraphy.model.request;
import com.project.theraphy.model.therapist;

import java.io.Serializable;

public class TherapistProfile extends AppCompatActivity implements Serializable {
    TextView Name , Gender ;
    FirebaseFirestore database;
    FirebaseAuth mAuth;
    ImageView img;
    DocumentReference theref;
    Button chatrequest , videorequest , TherapistDetail;
    TextView Total_Theraphy, Total_Patient, Specialist,Star;
    private StorageReference SR;
    private FirebaseStorage FS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_profile);

        String id = (String) getIntent().getSerializableExtra("therapist");

        mAuth=FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        Name = findViewById(R.id.TheProName);
        Gender = findViewById(R.id.Therapist_gender);

        Total_Theraphy = findViewById(R.id.total_theraphysession);
        Total_Patient = findViewById(R.id.patient_from_therapist);
        Specialist = findViewById(R.id.Specialist_therapist);
        Star = findViewById(R.id.Star_of_Therapist);
        TherapistDetail = findViewById(R.id.TherapistDetailButton);

        img = findViewById(R.id.TheProImage);
        FS = FirebaseStorage.getInstance();
        SR = FS.getReference();
        chatrequest = findViewById(R.id.ChatSessionRequest);
        theref = database.collection("Therapists").
                document(id);

        chatrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();
                        therapist the = doc.toObject(therapist.class);
                        boolean contain=false;
                        for (int i=0; i < the.getRequests().size();i++){
                            request req1 = the.getRequests().get(i);
                            if(req1.getPid().equals(mAuth.getCurrentUser().getUid())){
                                if(req1.getConfirmation()==0){
                                    contain=true;
                                }
                            }
                        }
                        if(contain==false){
                            request req = new request();
                            req.setConfirmation(0);
                            req.setPid(mAuth.getCurrentUser().getUid());
                            req.setRequest_type(0);
                            req.setTid(the.getTid());
                            database.collection("Patients").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot doc = task.getResult();
                                    patient p = new patient();
                                    p= doc.toObject(patient.class);
                                    req.setR_patient(p);
                                    req.setT_name(the.getName());
                                    database.collection("Patients").
                                            document(mAuth.getCurrentUser().getUid()).update("requests", FieldValue.arrayUnion(req));
                                    theref.update("requests", FieldValue.arrayUnion(req));
                                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(),"You Already Create a Request to "+the.getName(),Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }
        });
        theref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot docsnap = task.getResult();
                therapist the = docsnap.toObject(therapist.class);
                Name.setText(the.getName());
                Gender.setText(the.getGender());
                Star.setText(Double.toString(the.getScore()));
                Specialist.setText(the.getSpecialist());
                if (the.getAll_patients()==null){
                    Total_Patient.setText("0");
                    Total_Theraphy.setText("0");
                }else{
                    Total_Patient.setText(Integer.toString(the.getAll_patients().size()));
                    Total_Theraphy.setText(Integer.toString(the.getAll_patients().size()));
                }
                StorageReference SRchild= SR.child("images/" + the.getTid());
                SRchild.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageURL = uri.toString();
                        Glide.with(getApplicationContext()).load(imageURL).into(img);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                TherapistDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("therapist", (therapist) the);
                        Fragment com1 = new TherapistDetail();
                        com1.setArguments(bundle);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().add(R.id.fragment_container, com1).commit();
                    }
                });


            }
        });




    }

}