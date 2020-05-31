package com.example.tinder.ui.main.View;

import com.example.tinder.mvp.BaseView;

public interface UserCenterView<T>  extends BaseView{

    void imageType(T data ,int code);
}
