package com.example.tinder.ui.welcome;

import android.os.Handler;

import com.example.tinder.ui.main.MainActivity;
import com.example.tinder.R;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.ui.user.LoginActivity;
import com.example.tinder.ui.welcome.persenter.WelcomePersenter;
import com.example.tinder.ui.welcome.view.WelcomeView;

public class WelcomeActivity extends BaseActivity<WelcomeView, WelcomePersenter> {


    @Override
    public boolean isRegister() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {

//        SharedPreferencesUtils.saveToken(this,"token",null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isToken()) {
                    startIntent(MainActivity.class,true);
                } else {
                    startIntent(LoginActivity.class,true);
                }
            }
        },1000);

    }

    @Override
    public void initData() {

    }

    @Override
    protected WelcomePersenter createPersenter() {
        return new WelcomePersenter();
    }

    @Override
    public void setData(Object data,String action) {

    }

    @Override
    public void error(String msg) {

    }
}
