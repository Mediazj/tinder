package com.example.tinder.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tinder.R;
import com.example.tinder.mvp.BasePersenter;
import com.example.tinder.mvp.BaseView;
import com.example.tinder.utils.SharedPreferencesUtils;

public abstract class BaseFragment<V extends BaseView, P extends BasePersenter> extends Fragment implements BaseView {

    private String title;

    public BaseFragment(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private View view;

    private P mPersenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        if (mPersenter == null) {
            mPersenter = createPersenter();
        }
        if (mPersenter != null) {
            mPersenter.bindView(this);
        }
        initView(view);
        initData();
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initData();

    protected abstract P createPersenter();

    public P getmPersenter() {
        return mPersenter;
    }

    public void onDestroy() {
        super.onDestroy();
        mPersenter.unBindView();
    }
    public void startIntent(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivity(intent);
    }

    public String getToken(){
        return SharedPreferencesUtils.getSaveToken(getActivity(),"token");
    }
}
