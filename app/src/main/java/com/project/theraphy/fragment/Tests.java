package com.project.theraphy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.theraphy.R;
import com.project.theraphy.controller.TestRecyclerController;

public class Tests extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tests,container,false);
        TestRecyclerController TeC = new TestRecyclerController(v);
        return v;
    }
}
