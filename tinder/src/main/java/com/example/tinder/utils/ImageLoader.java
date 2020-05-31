package com.example.tinder.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {
    public static void setImage(Context context, String imageUrl, ImageView imageView) {
        if (Utils.isEmpty(imageUrl)) {
            return;
        }
        if (Utils.isEmpty(imageView)) {
            return;
        }
        RequestOptions options = RequestOptions.bitmapTransform(new RoundedCorners(15));
        Glide.with(context).load(imageUrl).apply(options).into(imageView);
    }

    public static void setImageCircle(Context context, String imageUrl, ImageView imageView) {
        if (Utils.isEmpty(imageUrl)) {
            return;
        }
        if (Utils.isEmpty(imageView)) {
            return;
        }
        RequestOptions options = RequestOptions.circleCropTransform();
        Glide.with(context).load(imageUrl).apply(options).into(imageView);
    }
}
