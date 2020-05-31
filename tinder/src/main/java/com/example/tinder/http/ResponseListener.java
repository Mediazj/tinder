package com.example.tinder.http;

public interface ResponseListener<T> {
    void onSuccess(T data);
    void onFail(String string);
}
