package com.example.tinder.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

public class Utils {
    public static boolean isEmpty(Object ob) {
        if (ob == null) {
            return true;
        }
        return false;
    }

    public static boolean isListEmpty(List<?> list) {
        if (list.size() != 0 && list != null) {
            return false;
        }
        return true;
    }

    public static String getDataUrl(Context context, Uri uri) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static void hideAllInputMethod(Activity act) {
        InputMethodManager imm = (InputMethodManager) act.getSystemService((Context.INPUT_METHOD_SERVICE));
        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }
}
