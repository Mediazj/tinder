package com.example.tinder.ui.message.adapter;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinder.R;
import com.example.tinder.ui.message.listener.OnItemListener;
import com.example.tinder.ui.message.model.MessageFgModel;
import com.example.tinder.utils.ImageLoader;
import com.example.tinder.utils.Utils;

import java.util.List;

public class UserMessageAdapter extends RecyclerView.Adapter  {

    private List<MessageFgModel.UserNew> userNewList;
    private List<MessageFgModel.UserOld> userOldList;
    private LinearLayoutManager layoutManager;
    private UserNewAdapter userNewAdapter;
    private OnItemListener onItemListener;

    public UserMessageAdapter(List<MessageFgModel.UserNew> userNewList, List<MessageFgModel.UserOld> userOldList) {
        this.userNewList = userNewList;
        this.userOldList = userOldList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutManager = new LinearLayoutManager(parent.getContext());
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            return new UserNewViewHolder(inflater.inflate(R.layout.item_user_new, parent, false));
        } else {
            return new UserOldViewHolder(inflater.inflate(R.layout.item_user_old, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserNewViewHolder) {
            UserNewViewHolder holder1 = (UserNewViewHolder) holder;
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            holder1.rvUserNew.setLayoutManager(layoutManager);
            if (userNewAdapter == null) {
                userNewAdapter = new UserNewAdapter(userNewList);
                holder1.rvUserNew.setAdapter(userNewAdapter);
            } else {
                userNewAdapter.notifyDataSetChanged();
            }

        } else if (holder instanceof UserOldViewHolder) {
            UserOldViewHolder holder1 = (UserOldViewHolder) holder;
            if (position == 1) {
                holder1.tvTitle.setVisibility(View.VISIBLE);
            } else {
                holder1.tvTitle.setVisibility(View.GONE);
            }
            if (!Utils.isListEmpty(userOldList)) {
                final MessageFgModel.UserOld userOld = userOldList.get(position - 1);
                ImageLoader.setImageCircle(holder.itemView.getContext(), userOld.getPhoto(), holder1.ivPhoto);
                holder1.tvName.setText(userOld.getName());
                holder1.tvContent.setText(userOld.getMessage());
                holder.itemView.setOnClickListener(view -> {
                    if (onItemListener != null) {
                        onItemListener.onItem(userOld);
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return Utils.isListEmpty(userOldList) ? 0 : userOldList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    class UserOldViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivPhoto;
        TextView tvName;
        TextView tvContent;

        public UserOldViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    class UserNewViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvUserNew;

        public UserNewViewHolder(@NonNull View itemView) {
            super(itemView);
            rvUserNew = itemView.findViewById(R.id.rv_user_new);
        }
    }
}
