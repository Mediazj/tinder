package com.example.tinder.ui.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tinder.mvp.BasePersenter;
import com.example.tinder.mvp.BaseView;
import com.example.tinder.utils.SharedPreferencesUtils;
import com.example.tinder.utils.Utils;
import com.example.tinder.utils.event.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity<V extends BaseView, P extends BasePersenter> extends AppCompatActivity implements BaseView {

    private P mPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            View view = window.getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (mPersenter == null) {
            mPersenter = createPersenter();
            mPersenter.bindView(this);
        }
        if (isRegister()) {
            EventBus.getDefault().register(this);
        }

        initView();
        initData();
    }

    public abstract boolean isRegister();

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    protected abstract P createPersenter();

    public P getmPersenter() {
        return mPersenter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventMessage eventMessage) {
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mPersenter != null) {
            mPersenter.unBindView();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public String getEditText(EditText et) {
        return et.getText().toString().trim();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void startIntent(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }

    public void startIntent(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
        finish();
    }


    public boolean isToken() {
        return SharedPreferencesUtils.getSaveToken(this, "token") != null ? true : false;
    }

    public String getToken() {
        return SharedPreferencesUtils.getSaveToken(this, "token");
    }

}
