package com.example.tinder.ui.main;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.tinder.R;
import com.example.tinder.http.ActionString;
import com.example.tinder.ui.main.View.MainView;
import com.example.tinder.ui.main.model.MainModel;
import com.example.tinder.ui.main.persenter.MainPersenter;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.ui.message.MessageListActivity;
import com.example.tinder.utils.Utils;
import com.example.tinder.utils.event.EventMessage;
import com.example.tinder.view.CardLayout;
import com.example.tinder.view.MainCard;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainView, MainPersenter> implements MainView,
        View.OnClickListener {

    private ImageView ivProfile;
    private ImageView ivLogo;
    private ImageView ivMessage;
    private ImageView ivPub01;
    private ImageView ivPub02;
    private ImageView ivPub03;
    private ImageView ivPub04;
    private ImageView ivPub05;
    private CardLayout mCardLayout;
    private List<MainCard> mainCards = new ArrayList<>();
    private int page = 1;
    private int pageSize = 20;
    private List<MainModel> modelList = new ArrayList<>();

    @Override
    public boolean isRegister() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        ivProfile = findViewById(R.id.iv_profile);
        ivLogo = findViewById(R.id.iv_logo);
        ivMessage = findViewById(R.id.iv_message);
        ivPub01 = findViewById(R.id.iv_pub01);
        ivPub02 = findViewById(R.id.iv_pub02);
        ivPub03 = findViewById(R.id.iv_pub03);
        ivPub04 = findViewById(R.id.iv_pub04);
        ivPub05 = findViewById(R.id.iv_pub05);
        mCardLayout = findViewById(R.id.cd_layout);

        ivProfile.setOnClickListener(this);
        ivMessage.setOnClickListener(this);
        ivPub01.setOnClickListener(this);
        ivPub02.setOnClickListener(this);
        ivPub03.setOnClickListener(this);
        ivPub04.setOnClickListener(this);
        ivPub05.setOnClickListener(this);

    }

    @Override
    public void initData() {
        getmPersenter().getPublicList(getToken(), page, pageSize);
    }

    @Override
    protected MainPersenter createPersenter() {
        return new MainPersenter();
    }

    @Override
    public void setData(Object data, String action) {
        if (action.contentEquals(ActionString.GET_PUBLIC_LIST)) {
            if (!Utils.isEmpty(data)) {
                modelList = (List<MainModel>) data;
                if (!Utils.isListEmpty(modelList)) {
                    for (MainModel model : modelList) {
                        MainCard mainCard = new MainCard(this);
                        mainCard.setModel(model);
                        mCardLayout.addCardView(mainCard);
                        mainCards.add(mainCard);
                    }
                }

            }
        }

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_profile:
                startIntent(UserCenterActivity.class);
                overridePendingTransition(R.anim.anim_bottom, R.anim.anim_bottom_not);
                break;
            case R.id.iv_message:
                startIntent(MessageListActivity.class);
                overridePendingTransition(R.anim.anim_right, R.anim.anim_left_out);
                break;
            case R.id.iv_pub01:
                upData();
                break;
            case R.id.iv_pub02:
                onClickLeft(R.mipmap.public_02);
                break;
            case R.id.iv_pub03:
                getmPersenter().getAction(getToken(),getUserId(),1);
                break;
            case R.id.iv_pub04:
                getmPersenter().getAction(getToken(),getUserId(),2);
                break;
            case R.id.iv_pub05:
                getmPersenter().getAction(getToken(),getUserId(),3);
                break;
            default:
                break;
        }
    }

    private int getUserId() {
        return Utils.isListEmpty(modelList) ? 0 : modelList.get(0).getId();
    }

    private void upData(){
        if(mCardLayout.getChildCount()!=0){
            mCardLayout.removeAllViews();
        }
        if(!Utils.isListEmpty(mainCards)){
            mainCards.clear();
        }
        if(!Utils.isListEmpty(modelList)){
            modelList.clear();
        }
        page++;
        getmPersenter().getPublicList(getToken(), page, pageSize);
    }

    private void onClickLeft(int imgId) {
        if (!Utils.isListEmpty(mainCards)) {
            mainCards.get(0).leftSliding(imgId);
        }
    }

    private void onClickRight(int imgId) {
        if (!Utils.isListEmpty(mainCards)) {
            mainCards.get(0).rightSliding(imgId);
        }
    }

    public void onEventMessage(EventMessage eventMessage) {
        if (!Utils.isEmpty(eventMessage)) {
            if (eventMessage.getCode() == 1) {
                mainCards.remove(0);
                if ((int) eventMessage.getData() == 1) {
                    upData();
                }
            } else if (eventMessage.getCode() == 2) {
                Intent intent = new Intent();
                intent.putExtra("userId", (int) eventMessage.getData());
                intent.setClass(this, UserDetailsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_bottom, R.anim.anim_bottom_not);
            }
        }
    }

    @Override
    public void acType(int type) {
        switch (type) {
            case 1:
                onClickLeft(R.mipmap.public_03);
                break;
            case 2:
                onClickRight(R.mipmap.public_04);
                break;
            case 3:
                onClickRight(R.mipmap.public_05);
                break;
            default:
                break;
        }
    }
}
