package com.project.theraphy.controller;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.theraphy.R;
import com.project.theraphy.chat.MesssageListAdapter;
import com.project.theraphy.model.chat;
import com.project.theraphy.model.comment;
import com.project.theraphy.model.message;
import com.project.theraphy.model.therapist;
import com.project.theraphy.view.HomeActivity;

import java.text.DecimalFormat;
import java.util.List;

public class CommentController {
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private Button sendcomment;
    private EditText comment_text, comment_star,comment_nickname;
    private TextView Therapist_name ;
    private ImageView therapist_Image;
    private StorageReference SR;
    private FirebaseStorage FS;



    public CommentController(View rootview,String tid) {
        sendcomment= rootview.findViewById(R.id.Send_comment_button);
        comment_text = rootview.findViewById(R.id.Comment_body);
        comment_star = rootview.findViewById(R.id.Comment_Star);
        comment_nickname = rootview.findViewById(R.id.comment_nickname);
        Therapist_name = rootview.findViewById(R.id.Comment_t_name);
        therapist_Image = rootview.findViewById(R.id.therapist_list_image);
        FS = FirebaseStorage.getInstance();
        SR = FS.getReference();
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        DocumentReference doc= firestore.collection("Therapists").document(tid);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                therapist the = snapshot.toObject(therapist.class);
                Therapist_name.setText(the.getName());
                StorageReference SRchild= SR.child("images/" + the.getTid());
                SRchild.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageURL = uri.toString();
                        Glide.with(rootview.getContext()).load(imageURL).into(therapist_Image);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(rootview.getContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                comment newCom = new comment();
                sendcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        newCom.setBody(comment_text.getText().toString());
                        newCom.setNick(comment_nickname.getText().toString());
                        newCom.setPid(mAuth.getCurrentUser().getUid());
                        newCom.setTid(the.getTid());
                        doc.update("comments", FieldValue.arrayUnion(newCom));
                        double oldtotal = the.getScore()*the.getScoreCount();
                        double newtotal = oldtotal + Double.parseDouble(comment_star.getText().toString());
                        double newScore = newtotal / (Double.valueOf((the.getScoreCount())+1));
                        DecimalFormat df = new DecimalFormat("#.##");

                        doc.update("scoreCount",FieldValue.increment(1));
                        doc.update("score", Double.valueOf(df.format(newScore)));
                        Intent i = new Intent(rootview.getContext(),HomeActivity.class);
                        rootview.getContext().startActivity(i);

                    }
                });


            }
        });



    }
}
