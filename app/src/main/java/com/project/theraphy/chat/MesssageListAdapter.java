package com.project.theraphy.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.Utils;
import com.project.theraphy.R;
import com.project.theraphy.model.message;
import com.project.theraphy.model.test;
import com.project.theraphy.view.Test_Screen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MesssageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<message> mMessageList;

    public MesssageListAdapter(Context context, List<message> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        message _message = (message) mMessageList.get(position);

        if (_message.isPatient()) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_me, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_other, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        message _message = (message) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(_message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(_message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText ,dateText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
            dateText = (TextView) itemView.findViewById(R.id.text_gchat_date_me);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);
        }

        void bind(message _message) {
            messageText.setText(_message.getBody());

            // Format the stored timestamp into a readable String using method.,
            String pattern1 = "MM-dd";
            String pattern2 = "HH:mm";
            SimpleDateFormat sD1 = new SimpleDateFormat(pattern1);
            SimpleDateFormat sD2 = new SimpleDateFormat(pattern2);
            Date time = _message.getDate();
            dateText.setText(sD1.format(time));
            timeText.setText(sD2.format(time));
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText , dateText;
        ImageView profileImage;
        FirebaseFirestore firestore;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            firestore= FirebaseFirestore.getInstance();
            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_other);
            dateText = (TextView) itemView.findViewById(R.id.text_gchat_date_other);
            profileImage = (ImageView) itemView.findViewById(R.id.image_gchat_profile_other);


        }

        void bind(message _message) {
            if (_message.getBody().equals("test")){
                firestore.collection("Tests").document("beier").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        test current = new test();
                        current = document.toObject(test.class);
                        Intent intent = new Intent(itemView.getContext(), Test_Screen.class);
                        intent.putExtra("test",(test)current);
                        itemView.getContext().startActivity(intent);
                    }
                });
            }else{
                messageText.setText(_message.getBody());
            }


            // Format the stored timestamp into a readable String using method.
            String pattern1 = "MM-dd";
            String pattern2 = "HH:mm";
            SimpleDateFormat sD1 = new SimpleDateFormat(pattern1);
            SimpleDateFormat sD2 = new SimpleDateFormat(pattern2);
            Date time = _message.getDate();
            dateText.setText(sD1.format(time));
            timeText.setText(sD2.format(time));

        }
    }
}