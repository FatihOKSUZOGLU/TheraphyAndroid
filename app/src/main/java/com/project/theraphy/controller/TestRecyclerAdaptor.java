package com.project.theraphy.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.project.theraphy.R;
import com.project.theraphy.model.request;
import com.project.theraphy.model.test;
import com.project.theraphy.view.Test_Screen;
import com.project.theraphy.view.TherapistProfile;

import java.io.Serializable;
import java.util.List;

public class TestRecyclerAdaptor extends RecyclerView.Adapter<TestRecyclerAdaptor.TestRecyclerHolder> {

    public interface OnItemClickListener {
        void onItemClick(test item);
    }

    private final List<test> list_test;
    private final TestRecyclerAdaptor.OnItemClickListener listener;



    public TestRecyclerAdaptor(List<test> _list_test, TestRecyclerAdaptor.OnItemClickListener listener) {
        this.listener=listener;
        this.list_test = _list_test;
    }


    @Override
    public TestRecyclerAdaptor.TestRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.requestitem, parent, false); //dikkat
        return new TestRecyclerAdaptor.TestRecyclerHolder(v);
    }



    @Override
    public void onBindViewHolder(TestRecyclerAdaptor.TestRecyclerHolder holder, int position) {
        holder.bind(list_test.get(position), listener);

    }

    static class TestRecyclerHolder extends RecyclerView.ViewHolder implements Serializable {

        private TextView Name;
        private TextView Confirmation;

        public TestRecyclerHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.requestName);
            Confirmation = itemView.findViewById(R.id.requestconfirm);
        }

        public void bind(test item, final TestRecyclerAdaptor.OnItemClickListener listener) {
            Name.setText(item.getTestname());
            Confirmation.setText("Click it!");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    test current = new test();
                    current = item;
                    Context c = v.getContext();
                    Intent intent = new Intent(c, Test_Screen.class);
                    intent.putExtra("test",(test)current);
                    c.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list_test.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
