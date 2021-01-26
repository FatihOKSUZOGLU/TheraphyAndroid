package com.project.theraphy.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.theraphy.R;
import com.project.theraphy.model.patient;

import java.io.ByteArrayOutputStream;

public class EditProfile extends AppCompatActivity {
    private Bitmap bitmap;
    private StorageReference SR;
    private FirebaseStorage FS;
    EditText Name,Gender,Phone,Age,About;
    ImageButton imbut;
    Button ChangeButton;
    FirebaseFirestore FFBdatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Name = findViewById(R.id.patient_name);
        Gender = findViewById(R.id.patient_gender);
        Phone = findViewById(R.id.patient_phone);
        Age = findViewById(R.id.patient_age);
        About = findViewById(R.id.patient_about);
        imbut = findViewById(R.id.therapist_list_image);
        FS = FirebaseStorage.getInstance();
        SR = FS.getReference();
        ChangeButton = findViewById(R.id.ChangeInfoButton);
        mAuth = FirebaseAuth.getInstance();
        imbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        FFBdatabase = FirebaseFirestore.getInstance();
        DocumentReference docref =  FFBdatabase.collection("Patients").document(mAuth.getCurrentUser().getUid());
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    patient p =  document.toObject(patient.class);
                    Name.setText(p.getName());
                    Gender.setText(p.getGender());
                    Phone.setText(p.getPhone());
                    Age.setText(Integer.toString(p.getAge()));
                    StorageReference SRchild= SR.child("images/" + mAuth.getCurrentUser().getUid());
                    SRchild.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            Glide.with(getApplicationContext()).load(imageURL).into(imbut);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    ChangeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            p.setName(Name.getText().toString());
                            p.setGender(Gender.getText().toString());
                            p.setPhone(Phone.getText().toString());
                            p.setAge(Integer.parseInt(Age.getText().toString()));
                            p.setAbout(About.getText().toString());
                            docref.set(p);
                        }
                    });


                }else{
                    Toast.makeText(getApplicationContext(),"Wrong Database argument!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void chooseFile(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&& resultCode==RESULT_OK && data!=null && data.getData() != null){
            Uri filepath = data.getData();
            imbut.setImageURI(filepath);
            uploadPicture(mAuth.getCurrentUser().getUid(),filepath);
        }
    }

    private void uploadPicture(final String id,final Uri photo) {
        StorageReference riversRef= SR.child("images/" + id);
        riversRef.putFile(photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });


    }
    public  String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream bytearray= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytearray);

        byte[] imagebyte = bytearray.toByteArray();
        String encodedimage = Base64.encodeToString(imagebyte,Base64.DEFAULT);
        return encodedimage;
    }
}