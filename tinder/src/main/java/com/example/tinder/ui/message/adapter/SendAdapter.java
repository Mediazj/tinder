package com.example.tinder.ui.message.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinder.R;
import com.example.tinder.ui.message.model.SendMessageModel;

import java.util.List;

public class SendAdapter extends RecyclerView.Adapter {

    private List<SendMessageModel.Messages> userMessage;

    public SendAdapter(List<SendMessageModel.Messages> userMessage) {
        this.userMessage = userMessage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            return new SendLeftViewHolder(layoutInflater.inflate(R.layout.item_left_send, parent, false));
        } else {
            return new SendRightViewHolder(layoutInflater.inflate(R.layout.item_right_send, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SendLeftViewHolder) {
            SendLeftViewHolder holder1 = (SendLeftViewHolder) holder;
            holder1.tvContentLeft.setText(userMessage.get(position).getMessage());
        } else if (holder instanceof SendRightViewHolder) {
            SendRightViewHolder holder1 = (SendRightViewHolder) holder;
            holder1.tvContentRight.setText(userMessage.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return userMessage.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(userMessage.get(position).getType()==1){
            return 0;
        }else{
            return 1;
        }
    }

    class SendLeftViewHolder extends RecyclerView.ViewHolder {
        TextView tvContentLeft;

        public SendLeftViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContentLeft = itemView.findViewById(R.id.tv_content_left);
        }
    }

    class SendRightViewHolder extends RecyclerView.ViewHolder {
        TextView tvContentRight;

        public SendRightViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContentRight = itemView.findViewById(R.id.tv_content_right);
        }
    }
}
