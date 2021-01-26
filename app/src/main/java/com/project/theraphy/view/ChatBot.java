package com.project.theraphy.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.project.theraphy.R;
import com.project.theraphy.chat.MesssageListAdapter;
import com.project.theraphy.model.chat;
import com.project.theraphy.model.message;
import com.project.theraphy.model.patient;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatBot extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private RecyclerView mMessageRecycler;
    private MesssageListAdapter mMessageAdapter;
    private List<message> messages ;
    private Button sendbutton;
    private EditText writenmessage;
    private chat che ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAuth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        sendbutton = findViewById(R.id.button_gchat_send);
        writenmessage = findViewById(R.id.edit_gchat_message);

        DocumentReference docref = firestore.collection("Chat").document("YenWSb8Ry1eJ0TTp1nJsQJDRdR93");
        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    che = snapshot.toObject(chat.class);
                    messages = che.getMessages();
                    MesssageListAdapter ms = new MesssageListAdapter(getApplicationContext(), messages);
                    mMessageRecycler.setAdapter(ms);
                    if (mMessageRecycler.getAdapter().getItemCount()-1>1){
                        mMessageRecycler.smoothScrollToPosition(mMessageRecycler.getAdapter().getItemCount()-1);
                    }
                    sendbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            message me = new message();
                            me.setBody(writenmessage.getText().toString());
                            me.setPid("YenWSb8Ry1eJ0TTp1nJsQJDRdR93");
                            me.setTid(che.getTid());
                            me.setPatient(true);
                            Date currentTime = Calendar.getInstance().getTime();
                            me.setDate(currentTime);
                            me.setTname(null);
                            try {
                                firestore.collection("Chat").document("YenWSb8Ry1eJ0TTp1nJsQJDRdR93").update("messages", FieldValue.arrayUnion(me));
                                writenmessage.setText("");
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {

                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

}