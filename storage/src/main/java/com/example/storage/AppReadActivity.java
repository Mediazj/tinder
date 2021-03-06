package com.example.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class AppReadActivity extends AppCompatActivity {

    private TextView tv_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_read);
        tv_app = (TextView) findViewById(R.id.tv_app);
        readAppMemory();
    }

    private void readAppMemory() {
        String desc = "全局内存中保存的信息如下：";
        MainApplication app = MainApplication.getInstance();
        Map<String, String> mapParam = app.mInfoMap;
        for (Map.Entry<String, String> item_map : mapParam.entrySet()) {
            desc = String.format("%s\n　%s的取值为%s",
                    desc, item_map.getKey(), item_map.getValue());
        }
        if (mapParam==null || mapParam.size()<=0) {
            desc = "全局内存中保存的信息为空";
        }
        tv_app.setText(desc);
    }

}

