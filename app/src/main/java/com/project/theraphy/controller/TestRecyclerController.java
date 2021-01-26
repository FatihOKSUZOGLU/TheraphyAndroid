package com.project.theraphy.controller;

import android.util.Log;
import android.view.View;

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
import com.project.theraphy.model.request;
import com.project.theraphy.model.test;
import com.project.theraphy.model.test_item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestRecyclerController extends AppCompatActivity implements Serializable {
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private List<test_item> list;
    private test_item pat;

    public TestRecyclerController(View rootview){

        recyclerView = rootview.findViewById(R.id.recyclertests);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
        mAuth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        firestore.collection("Tests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<test> tests= new ArrayList<test>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        tests.add(document.toObject(test.class));
                    }
                } else {

                }
                recyclerView.setAdapter(new TestRecyclerAdaptor(tests, new TestRecyclerAdaptor.OnItemClickListener() {
                    @Override
                    public void onItemClick(test item) {

                    }
                }));
            }
        });
    }
}
