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

public class QuestionsAdaptor extends RecyclerView.Adapter<QuestionsAdaptor.questionViewHolder> {

        public interface OnItemClickListener {
            void onItemClick(test_item item);
        }

        private final List<test_item> list_question;
        private final QuestionsAdaptor.OnItemClickListener listener;
        private final test_to result;



    public QuestionsAdaptor(test_to _result, List<test_item> _list_question, QuestionsAdaptor.OnItemClickListener listener) {
            this.listener=listener;
            this.list_question = _list_question;
            this.result=_result;
        }


        @Override
        public QuestionsAdaptor.questionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false); //dikkat

            return new QuestionsAdaptor.questionViewHolder(v);
        }



        @Override
        public void onBindViewHolder(QuestionsAdaptor.questionViewHolder holder, int position) {
            holder.bind(position,result,list_question.get(position), listener);

        }

        static class questionViewHolder extends RecyclerView.ViewHolder {

            private TextView Question;
            private TextView Answer;

            public questionViewHolder(View itemView) {
                super(itemView);
                Question = itemView.findViewById(R.id.question_text);
                Answer = itemView.findViewById(R.id.answer_text);
            }

            public void bind(int pos,test_to result, test_item item, final QuestionsAdaptor.OnItemClickListener listener) {
                Question.setText(item.getQuestion());
                Answer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            result.setAnswer(Answer.getText().toString(),pos);
                        }
                    }
                });
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
