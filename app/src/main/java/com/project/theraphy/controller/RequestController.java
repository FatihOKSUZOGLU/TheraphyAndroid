package com.project.theraphy.controller;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.theraphy.R;
import com.project.theraphy.model.patient;
import com.project.theraphy.model.request;
import com.project.theraphy.model.therapist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestController extends AppCompatActivity implements Serializable{
    private RecyclerView recyclerView;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    List<request> list;
    patient pat;

    public RequestController(View rootview){

        recyclerView = rootview.findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
        mAuth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        firestore.collection("Patients").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                pat = documentSnapshot.toObject(patient.class);
                list = pat.getRequests();
                recyclerView.setAdapter(new RequestAdaptor(list, new RequestAdaptor.OnItemClickListener() {
                    @Override
                    public void onItemClick(request item) {

                    }
                }));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
