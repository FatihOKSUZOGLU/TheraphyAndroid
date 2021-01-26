package com.project.theraphy.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.theraphy.R;
import com.project.theraphy.controller.QuestionsAdaptor;
import com.project.theraphy.controller.RequestAdaptor;
import com.project.theraphy.fragment.Tests;
import com.project.theraphy.model.patient;
import com.project.theraphy.model.request;
import com.project.theraphy.model.test;
import com.project.theraphy.model.test_item;
import com.project.theraphy.model.test_to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test_Screen extends AppCompatActivity implements Serializable {
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private Button SaveButton;
    private FirebaseAuth mAuth;
    private List<test_item> list;
    private test_item pat;
    private TextView Head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__screen);

        test _test = (test) getIntent().getSerializableExtra("test");
        test_to result = new test_to();
        result.setTest_name(_test.getTestname());
        result.setResulted(false);
        result.setTest_results(new ArrayList<String>());
        list= _test.getItems();
        Head = findViewById(R.id.HeadTestScreen);
        Head.setText(_test.getTestname());
        result.setTest(list);
        Toast.makeText(getApplicationContext(),_test.getTestname(),Toast.LENGTH_SHORT).show();
        SaveButton = findViewById(R.id.Save_Button);
        recyclerView = findViewById(R.id.recycler_questions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAuth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        recyclerView.setAdapter(new QuestionsAdaptor(result,list, new QuestionsAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(test_item item) {

            }
        }));
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAuth.getCurrentUser()==null){
                    Intent i = new Intent(getApplicationContext(), Register.class);
                    i.putExtra("result",(test_to) result);
                    startActivity(i);
                }else{
                    result.setTest_pid(mAuth.getCurrentUser().getUid());
                    firestore.collection("Test_to").document(mAuth.getCurrentUser().getUid()).set(result);
                    Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}