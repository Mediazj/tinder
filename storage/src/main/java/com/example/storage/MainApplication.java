package com.example.storage;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;

public class MainApplication extends Application {
    private final static String TAG = "MainApplication";
    private static MainApplication mApp;
    public HashMap<String, String> mInfoMap = new HashMap<String, String>();

    public static MainApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate");
        super.onTerminate();
    }

    public HashMap<Long, Bitmap> mIconMap = new HashMap<Long, Bitmap>();
}
