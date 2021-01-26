package com.project.theraphy.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.theraphy.R;
import com.project.theraphy.fragment.Account;
import com.project.theraphy.fragment.Inexperienced;
import com.project.theraphy.fragment.MyRequests;
import com.project.theraphy.fragment.MyTheraphy;
import com.project.theraphy.fragment.Seniors;
import com.project.theraphy.fragment.Session;
import com.project.theraphy.fragment.Tests;
import com.project.theraphy.fragment.TherapistDetail;
import com.project.theraphy.model.patient;
import com.project.theraphy.model.test;
import com.project.theraphy.model.test_item;
import com.project.theraphy.model.therapist;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DrawerLayout drawer;
    patient cur_pat = new patient();
    TextView homeName, homeMail;
    FirebaseFirestore FFdb;
    DocumentReference Patref;
    ImageView HomeImage;
    private StorageReference SR;
    private FirebaseStorage FS;
    private boolean Sactive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        mAuth = FirebaseAuth.getInstance();
        FFdb = FirebaseFirestore.getInstance();
        FS = FirebaseStorage.getInstance();
        SR = FS.getReference();
        /*
        test_item t = new test_item();
        t.setQuestion("");
        t.setAnswer("");
        DocumentReference doc = FFdb.collection("Tests").document("beier");

        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot d = task.getResult();
                List<test_item> list = new ArrayList<test_item>();
                test tes = new test();
                tes = d.toObject(test.class);
                list = tes.getItems();
                for(int i = 0; i<=57 ; i++){
                    list.add(t);
                }
                doc.update("items",list);
            }
        });

        */
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        homeName = (TextView) headerView.findViewById(R.id.nameHomeText);
        homeMail = (TextView) headerView.findViewById(R.id.mailHomeText);
        HomeImage = (ImageView) headerView.findViewById(R.id.HomeImage);

        ActionBarDrawerToggle toglle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toglle);
        toglle.syncState();



        Patref = FFdb.collection("Patients").document(mAuth.getCurrentUser().getUid());
        Patref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                cur_pat = document.toObject(patient.class);
                homeName.setText(cur_pat.getName());
                homeMail.setText(cur_pat.getEmail());
                    StorageReference SRchild= SR.child("images/" + mAuth.getCurrentUser().getUid());
                    SRchild.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String imageURL = uri.toString();
                            Glide.with(getApplicationContext()).load(imageURL).into(HomeImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.Seniors:
                Bundle bundle = new Bundle();
                bundle.putString("String","S");
                Fragment com1 = new Seniors();
                com1.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        com1).commit();
                break;
            case R.id.Inexperienced:
                Bundle bundle1 = new Bundle();
                bundle1.putString("String","I");
                Fragment com2 = new Seniors();
                com2.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        com2).commit();
                break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Account()).commit();
                break;
            case R.id.Tests:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Tests()).commit();
                break;
            case R.id.myrequests:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyRequests()).commit();
                break;
            case R.id.mytheraphy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyTheraphy()).commit();
                break;
            case R.id.current_session:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Session()).commit();


                break;
            case R.id.exit:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

    }
}