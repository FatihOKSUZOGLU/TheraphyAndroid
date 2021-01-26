package com.project.theraphy.controller;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.theraphy.R;
import com.project.theraphy.model.request;

import java.util.List;

public class RequestAdaptor extends RecyclerView.Adapter<RequestAdaptor.RequestViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(request item);
    }

    private final List<request> list_request;
    private final OnItemClickListener listener;



    public RequestAdaptor(List<request> _list_request, OnItemClickListener listener) {
        this.listener=listener;
        this.list_request = _list_request;
    }


    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.requestitem, parent, false); //dikkat

        return new RequestViewHolder(v);
    }



    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        holder.bind(list_request.get(position), listener);

    }

    static class RequestViewHolder extends RecyclerView.ViewHolder {

        private TextView Name;
        private TextView Confirmation,sessiontype;

        public RequestViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.requestName);
            Confirmation = itemView.findViewById(R.id.requestconfirm);
            sessiontype = itemView.findViewById(R.id.SessionType);
        }

        public void bind(request item, final OnItemClickListener listener) {
            Name.setText(item.getT_name());
            int conf = item.getConfirmation();
            if(conf==0){
                Confirmation.setText("Waiting.");
            }else if (conf==1){
                Confirmation.setText("Accepted.");
            }else{
                Confirmation.setText("Rejected.");
            }
            if(item.getRequest_type()==0){
                sessiontype.setText("Chat");
            }else if (item.getRequest_type()==1){
                sessiontype.setText("Video");
            }
        }
    }

    @Override
    public int getItemCount() {
        return list_request.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}