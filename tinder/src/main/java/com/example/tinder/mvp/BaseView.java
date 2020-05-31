package com.example.tinder.mvp;

public interface BaseView<T> {
    void setData(T data,String aciton);
    void error(String msg);

}
