package com.project.theraphy.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.theraphy.R;
import com.project.theraphy.fragment.Session;
import com.project.theraphy.model.test_item;
import com.project.theraphy.model.test_to;

import java.util.List;

public class DetailRecyclerAdaptor extends RecyclerView.Adapter<DetailRecyclerAdaptor.DetailViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(test_item item);
    }

    private final List<test_item> list_question;
    private final DetailRecyclerAdaptor.OnItemClickListener listener;



    public DetailRecyclerAdaptor(List<test_item> _list_question, DetailRecyclerAdaptor.OnItemClickListener listener) {
        this.listener=listener;
        this.list_question = _list_question;
    }


    @Override
    public DetailRecyclerAdaptor.DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.therapist_detail_item, parent, false); //dikkat

        return new DetailRecyclerAdaptor.DetailViewHolder(v);
    }



    @Override
    public void onBindViewHolder(DetailRecyclerAdaptor.DetailViewHolder holder, int position) {
        holder.bind(list_question.get(position), listener);

    }

    static class DetailViewHolder extends RecyclerView.ViewHolder {

        private TextView Question;
        private TextView Answer;

        public DetailViewHolder(View itemView) {
            super(itemView);
            Question = itemView.findViewById(R.id.DetailHeader);
            Answer = itemView.findViewById(R.id.DetailText);
        }

        public void bind(test_item item, final DetailRecyclerAdaptor.OnItemClickListener listener) {
            Question.setText(item.getQuestion());
            Answer.setText(item.getAnswer());
        }
    }

    @Override
    public int getItemCount() {
        return list_question.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
