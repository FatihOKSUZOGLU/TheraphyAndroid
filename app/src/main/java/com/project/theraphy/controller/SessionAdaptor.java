package com.project.theraphy.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.theraphy.R;
import com.project.theraphy.fragment.Comment;
import com.project.theraphy.model.chat;
import com.project.theraphy.model.request;
import com.project.theraphy.model.therapist;

import java.util.List;

public class SessionAdaptor extends RecyclerView.Adapter<SessionAdaptor.SessionViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(chat item);
    }

    private final List<chat> list_chat;
    private final SessionAdaptor.OnItemClickListener listener;



    public SessionAdaptor(List<chat> _list_chat, SessionAdaptor.OnItemClickListener listener) {
        this.listener=listener;
        this.list_chat = _list_chat;
    }


    @Override
    public SessionAdaptor.SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.requestitem, parent, false); //dikkat

        return new SessionAdaptor.SessionViewHolder(v);
    }



    @Override
    public void onBindViewHolder(SessionAdaptor.SessionViewHolder holder, int position) {
        holder.bind(list_chat.get(position), listener);

    }

    static class SessionViewHolder extends RecyclerView.ViewHolder {

        private TextView Name;
        private TextView Progress,sessiontype;
        private FirebaseFirestore firestore;

        public SessionViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.requestName);
            Progress = itemView.findViewById(R.id.requestconfirm);
            sessiontype = itemView.findViewById(R.id.SessionType);
        }

        public void bind(chat item, final SessionAdaptor.OnItemClickListener listener) {
            firestore= FirebaseFirestore.getInstance();
            firestore.collection("Therapists").document(item.getTid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot snapshot) {
                    therapist the = snapshot.toObject(therapist.class);
                    Name.setText(the.getName());
                }
            });
            if (item.isActive()){
                Progress.setText("In Progress..");
            }else{
                Progress.setText("Ended.");
            }
            sessiontype.setText("Chat S.");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return list_chat.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
