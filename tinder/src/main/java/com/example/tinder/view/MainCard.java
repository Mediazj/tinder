package com.example.tinder.view;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.tinder.R;
import com.example.tinder.ui.main.model.MainModel;
import com.example.tinder.utils.Utils;
import com.example.tinder.utils.event.EventMessage;

import org.greenrobot.eventbus.EventBus;

public class MainCard extends FrameLayout implements View.OnTouchListener  {

    private static final int TIME = 300;
    private static final float ROTATION = 30.0f;
    private static final float LEFT_DEFAULTX = -300.0f;
    private static final float RIGHT_DEFAULTX = 300.0f;

    private CardView cdViewMain;
    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvAge;
    private TextView tvWork;
    private ImageView ivDefault;
    private TextView tvLike;
    private TextView tvNope;

    private float oldX;
    private float oldY;
    private float newX;
    private float newY;
    private float dX;
    private float sceWh;
    private float leftBoy;
    private float rightBoy;
    private MainModel mainModel;

    public MainCard(@NonNull Context context) {
        super(context);
        initView();
    }

    public MainCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MainCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (!isInEditMode()) {
            inflate(getContext(), R.layout.card_view, this);
            cdViewMain = findViewById(R.id.cd_view_main);
            ivPhoto = findViewById(R.id.iv_photo);
            ivDefault = findViewById(R.id.iv_default);
            tvName = findViewById(R.id.tv_name);
            tvAge = findViewById(R.id.tv_age);
            tvWork = findViewById(R.id.tv_work);
            tvLike = findViewById(R.id.tv_like);
            tvNope = findViewById(R.id.tv_nope);
            setOnTouchListener(this);
            sceWh = getSceWh(getContext());
            leftBoy = sceWh * (1.0f / 6.0f);
            rightBoy = sceWh * (5.0f / 6.0f);
            ivDefault.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!Utils.isEmpty(mainModel)){
                        EventBus.getDefault().post(new EventMessage(2,mainModel.getId()));
                    }
                }
            });

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setOnTouchListener(null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = event.getX();
                oldY = event.getY();
                v.clearAnimation();
                return true;
            case MotionEvent.ACTION_UP:
                if (isLeftBoy(v)) {
                    startAnimate(v, -(sceWh * 2));
                } else if (isRightBoy(v)) {
                    startAnimate(v, (sceWh * 2));
                } else {
                    updateView(v);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                newX = event.getX();
                newY = event.getY();
                dX = newX - oldX;
                v.setX(v.getX() + dX);
                v.setY(v.getY() + (newY - oldY));
                cardRotation(v, v.getX());
                setTvAlpha(v.getX() + dX);
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void setTvAlpha(float dx) {
        float alpha = dx / (sceWh * 0.5f);
        tvLike.setAlpha(alpha);
        tvNope.setAlpha(alpha);
    }

    private void cardRotation(View view, float px) {
        float rotation = ROTATION * px / sceWh;
        int ht = view.getHeight() / 2;
        if (oldY < ht) {
            view.setRotation(rotation);
        } else {
            view.setRotation(-rotation);
        }
    }

    private boolean isLeftBoy(View view) {
        return (view.getX() + (view.getWidth() / 2)) < leftBoy;
    }

    private boolean isRightBoy(View view) {
        return (view.getX() + (view.getWidth() / 2)) > rightBoy;
    }

    private void updateView(View view) {
        view.animate()
                .x(0)
                .y(0)
                .rotation(0)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(TIME);
    }

    public float getSceWh(Context context) {
        Point point = new Point();
        ((Activity) context).getWindowManager().getDefaultDisplay().getSize(point);
        return point.x;
    }

    private void startAnimate(final View view, float whX) {
        view.animate()
                .x(whX)
                .y(0)
                .setDuration(TIME)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewGroup viewGroup = (ViewGroup) view.getParent();
                        if (viewGroup != null) {
                            viewGroup.removeView(view);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    public void leftSliding(int imgId) {
        startAnimate(this, -(sceWh * 2));
        cardRotation(this, LEFT_DEFAULTX);
        setTvAlpha(LEFT_DEFAULTX + (newX - oldX));
        tvLike.setBackgroundResource(imgId);
    }

    public void rightSliding(int imgId) {
        startAnimate(this, (sceWh * 2));
        cardRotation(this, RIGHT_DEFAULTX);
        setTvAlpha(RIGHT_DEFAULTX + (newX - oldX));
        tvNope.setBackgroundResource(imgId);
    }

    public void setModel(MainModel model) {
        if (!Utils.isEmpty(model)) {
            this.mainModel = model;
            if(!Utils.isEmpty(model.getPhoto())) {
                Glide.with(getContext()).load(model.getPhoto()).into(ivPhoto);
            }
            if(!Utils.isEmpty(model.getUser_name())) {
                tvName.setText(model.getUser_name());
            }
            if(!Utils.isEmpty(model.getUser_age())) {
                tvAge.setText(model.getUser_age());
            }
            if(!Utils.isEmpty(model.getUser_work())) {
                tvWork.setText(model.getUser_work());
            }
        }
    }
}
