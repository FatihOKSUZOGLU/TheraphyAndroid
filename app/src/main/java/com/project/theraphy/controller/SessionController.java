package com.project.theraphy.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.project.theraphy.fragment.Comment;
import com.project.theraphy.model.chat;
import com.project.theraphy.model.patient;
import com.project.theraphy.model.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SessionController extends AppCompatActivity implements Serializable {
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    public SessionController(View rootview,FragmentTransaction ft){

        recyclerView = rootview.findViewById(R.id.recyclerviewsession);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
        mAuth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        firestore.collection("Chat").whereEqualTo("pid",mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<chat> list= new ArrayList<chat>();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        if (!document.toObject(chat.class).isActive())
                            list.add(document.toObject(chat.class));
                    }
                    recyclerView.setAdapter(new SessionAdaptor(list, new SessionAdaptor.OnItemClickListener() {
                        @Override
                        public void onItemClick(chat item) {
                            Bundle bundle = new Bundle();
                            bundle.putString("tid",item.getTid());
                            Comment com = new Comment();
                            com.setArguments(bundle);
                            ft.replace(R.id.fragment_container, com);
                            ft.commit();


                        }
                    }));

                }else{

                }
            }
        });
    }

}
