package com.project.theraphy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.theraphy.R;
import com.project.theraphy.controller.AccountController;
import com.project.theraphy.controller.CommentController;

public class Comment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview =inflater.inflate(R.layout.comment,container,false);
        String strtext= getArguments().getString("tid");
        CommentController Commentc = new CommentController(rootview,strtext);
        return rootview;
    }
}
