package com.example.tinder.ui.message.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinder.R;
import com.example.tinder.ui.message.model.FeedFgModel;
import com.example.tinder.utils.ImageLoader;
import com.example.tinder.utils.Utils;

import java.util.List;

public class UserFeedAdapter extends RecyclerView.Adapter<UserFeedAdapter.UserFeedViewHolder> {

    private List<FeedFgModel> userFeed;

    public UserFeedAdapter(List<FeedFgModel> userFeed) {
        this.userFeed = userFeed;
    }

    @NonNull
    @Override
    public UserFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new UserFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFeedViewHolder holder, int position) {
        if (holder instanceof UserFeedViewHolder) {
            if (position == 0) {
                holder.ivType.setBackgroundResource(R.mipmap.public_04);
            } else if (position == 1) {
                holder.ivType.setBackgroundResource(R.mipmap.public_05);
            }
            if(!Utils.isListEmpty(userFeed)){
                FeedFgModel model=userFeed.get(position);
                ImageLoader.setImageCircle(holder.itemView.getContext(),model.getPhoto(),holder.ivPhoto);
                holder.tvName.setText(model.getName());
                holder.tvTime.setText(model.getTime());
                if(!Utils.isEmpty(model.getType())){
                    if(model.getType()==1){
                        holder.ivType.setBackgroundResource(R.mipmap.public_05);
                    }else if(model.getType()==2){
                        holder.ivType.setBackgroundResource(R.mipmap.public_04);
                    }else if(model.getType()==3){
                        holder.ivType.setBackgroundResource(R.mipmap.public_03);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return Utils.isListEmpty(userFeed) ? 0 : userFeed.size();
    }

    class UserFeedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvName;
        TextView tvTime;
        ImageView ivType;

        public UserFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto=itemView.findViewById(R.id.iv_photo);
            tvName=itemView.findViewById(R.id.tv_name);
            tvTime=itemView.findViewById(R.id.tv_time);
            ivType = itemView.findViewById(R.id.iv_type);
        }
    }
}
