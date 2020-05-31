package com.example.tinder.ui.message.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinder.R;
import com.example.tinder.ui.message.model.MessageFgModel;
import com.example.tinder.utils.ImageLoader;
import com.example.tinder.utils.Utils;

import java.util.List;

public class UserNewAdapter extends RecyclerView.Adapter {

    private List<MessageFgModel.UserNew> userNewList;

    public UserNewAdapter(List<MessageFgModel.UserNew> userNewList) {
        this.userNewList = userNewList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserNewViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_new_cil, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserNewViewHolder) {
            UserNewViewHolder userNew = (UserNewViewHolder) holder;
            if (!Utils.isListEmpty(userNewList)) {
                MessageFgModel.UserNew userNewModel = userNewList.get(position);
                ImageLoader.setImageCircle(userNew.itemView.getContext(), userNewModel.getPhoto(), userNew.ivPhoto);
                userNew.tvName.setText(userNewModel.getName());
            }
        }
    }

    @Override
    public int getItemCount() {
        return Utils.isListEmpty(userNewList) ? 0 : userNewList.size();
    }

    class UserNewViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvName;

        public UserNewViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
