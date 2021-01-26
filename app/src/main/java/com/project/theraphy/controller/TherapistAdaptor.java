package com.project.theraphy.controller;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.theraphy.R;
import com.project.theraphy.model.therapist;
import com.project.theraphy.view.TherapistProfile;

import java.io.Serializable;
import java.util.List;

public class TherapistAdaptor extends RecyclerView.Adapter<TherapistAdaptor.TherapistViewHolder> implements Serializable{

    public interface OnItemClickListener {
        void onItemClick(therapist item);
    }

    private final List<therapist> list_person;
    private final OnItemClickListener listener;



    public TherapistAdaptor(List<therapist> list_therapist, OnItemClickListener listener) {
        this.listener=listener;
        this.list_person = list_therapist;
    }


    @Override
    public TherapistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_therapist, parent, false); //dikkat

        return new TherapistViewHolder(v);
    }



    @Override
    public void onBindViewHolder(TherapistViewHolder holder, int position) {
        holder.bind(list_person.get(position), listener);

    }

    static class TherapistViewHolder extends RecyclerView.ViewHolder implements Serializable {
        private StorageReference SR;
        private FirebaseStorage FS;
        private TextView Name;
        private TextView Star;
        private ImageView img;
        private Button viewP;
        private therapist clickedthe;

        public TherapistViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.itemName);
            Star = itemView.findViewById(R.id.itemrightinfo);
            img = itemView.findViewById(R.id.therapist_list_image);
            viewP = itemView.findViewById(R.id.view_profile);
            FS = FirebaseStorage.getInstance();
            SR = FS.getReference();
        }

        public void bind(therapist therap, final OnItemClickListener listener) {
            Name.setText(therap.getName());
            Star.setText(Double.toString(therap.getScore()));
            StorageReference SRchild= SR.child("images/" + therap.getTid());
            SRchild.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String imageURL = uri.toString();
                    Glide.with(itemView.getContext()).load(imageURL).into(img);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(itemView.getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            });


            viewP.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Context c = v.getContext();
                    Intent intent = new Intent(c, TherapistProfile.class);
                    intent.putExtra("therapist",therap.getTid());
                    c.startActivity(intent);
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return list_person.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}