package com.project.theraphy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.theraphy.R;
import com.project.theraphy.controller.AccountController;
import com.project.theraphy.controller.DetailController;
import com.project.theraphy.model.therapist;

import java.io.Serializable;

public class TherapistDetail extends Fragment implements Serializable {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview =inflater.inflate(R.layout.detail_screen,container,false);

        therapist the = (therapist) getArguments().getSerializable("therapist");

        DetailController dc = new DetailController(rootview,the);
        return rootview;
    }

}
