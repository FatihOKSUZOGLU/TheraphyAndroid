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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.theraphy.R;
import com.project.theraphy.model.therapist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TherapistController extends AppCompatActivity implements Serializable{
    private RecyclerView recyclerView;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    List<therapist> list;
    therapist the ;
    Context mContext;

    public TherapistController(View rootview,String type){
        recyclerView = rootview.findViewById(R.id.recyclerViewTherapist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
        mAuth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        firestore.collection("Therapists").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        therapist the = document.toObject(therapist.class);
                        if (type.equals("S")&&!the.isIntern()){
                            list.add(the);
                        } else if(type.equals("I")&&the.isIntern()){
                            list.add(the);
                        }
                    }
                    recyclerView.setAdapter(new TherapistAdaptor(list, new TherapistAdaptor.OnItemClickListener() {
                        @Override
                        public void onItemClick(therapist item) {

                        }
                    }));
                } else {
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
