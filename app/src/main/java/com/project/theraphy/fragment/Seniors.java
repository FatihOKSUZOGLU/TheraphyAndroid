package com.project.theraphy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.theraphy.R;
import com.project.theraphy.controller.TherapistController;
import com.project.theraphy.model.therapist;

public class Seniors extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.seniors,container,false);
        String s= (String) getArguments().getSerializable("String");
        TherapistController procont = new TherapistController(rootview,s);
        return rootview;
    }
}
