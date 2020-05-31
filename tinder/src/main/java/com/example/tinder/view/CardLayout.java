package com.example.tinder.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tinder.utils.event.EventMessage;

import org.greenrobot.eventbus.EventBus;

public class CardLayout extends FrameLayout {
    public CardLayout(@NonNull Context context) {
        super(context);
    }

    public CardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addCardView(MainCard mainCard){
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mainCard,0,layoutParams);
    }

    public void removeView(View view){
        super.removeView(view);
        EventBus.getDefault().post(new EventMessage(1,getChildCount()));
    }
}
