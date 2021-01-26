package com.project.theraphy.controller;

import android.content.Context;
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
import com.project.theraphy.model.test_item;
import com.project.theraphy.model.therapist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailController extends AppCompatActivity implements Serializable {
    private RecyclerView recyclerView;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    List<test_item> list;
    test_item current;

    public DetailController(View rootview,therapist the){
        recyclerView = rootview.findViewById(R.id.therapist_detail_recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
        mAuth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        list = new ArrayList<test_item>();
        if (the.getabout().length()>=1){
            current  = new test_item();
            current.setQuestion("About");
            current.setAnswer(the.getabout());
            list.add(current);
        }
        if(the.getEducation()!=null){
            current  = new test_item();
            current.setQuestion("Educations");
            String Educations ="";
            for (int i=0;  i < the.getEducation().size();i++){
                Educations += Integer.toString(i) + " ) "+ the.getEducation().get(i) + "\n";
            }
            current.setAnswer(Educations);
            list.add(current);
        }if (the.getExpertise()!=null){
            current  = new test_item();
            current.setQuestion("Expertises");
            String Expertises ="";
            for (int i=0;  i < the.getExpertise().size();i++){
                Expertises += Integer.toString(i) + " ) "+ the.getExpertise().get(i) + "\n";
            }
            current.setAnswer(Expertises);
            list.add(current);
        }if (the.getComments()!=null){
            current  = new test_item();
            current.setQuestion("Comments");
            String Comments ="";
            for (int i=0;  i < the.getComments().size();i++){
                Comments += the.getComments().get(i).getNick() + "\nComment: " + the.getComments().get(i).getBody()+ "\n\n";
            }
            current.setAnswer(Comments);
            list.add(current);
        }




        recyclerView.setAdapter(new DetailRecyclerAdaptor(list, new DetailRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(test_item item) {

            }
        }));

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}