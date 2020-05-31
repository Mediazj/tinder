package com.example.tinder.ui.main.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.tinder.utils.Utils;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {

    private List<String> photoUrl;

    public PhotoAdapter(List<String> photoUrl) {
        this.photoUrl = photoUrl;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView ivPhoto = new ImageView(container.getContext());
        ivPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
        if(!Utils.isListEmpty(photoUrl)){
            Glide.with(container.getContext()).load(photoUrl.get(position)).into(ivPhoto);
        }
        container.addView(ivPhoto);
        return ivPhoto;
    }

    @Override
    public int getCount() {
        return photoUrl.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
