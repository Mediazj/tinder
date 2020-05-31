package com.example.tinder.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    private static final String TOKEN_STR="save_token";

    public static boolean saveToken(Context context,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_STR,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        return editor.commit();
    }

    public static String getSaveToken(Context context,String key){
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_STR,
                Context.MODE_PRIVATE);
        return preferences.getString(key,null);
    }

}
