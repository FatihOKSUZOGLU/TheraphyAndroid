package com.project.theraphy.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.theraphy.R;
import com.project.theraphy.controller.SessionController;

public class MyTheraphy extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_my_theraphy,container,false);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        SessionController ses = new SessionController(v,ft);

        return v;
    }
}