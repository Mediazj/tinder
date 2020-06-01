package com.example.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.storage.util.DateUtil;

public class MenuOptionActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MenuOptionActivity";
    private TextView tv_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_option);
        tv_option = (TextView) findViewById(R.id.tv_option);
        findViewById(R.id.btn_option).setOnClickListener(this);

        setRandomTime();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_option) {
            //注意：如果当前页面继承自AppCompatActivity，并且appcompat版本不低于22.1.0
            //那么调用openOptionsMenu方法将不会弹出菜单。这应该是Android的一个bug
            openOptionsMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_change_time) {
            setRandomTime();
        } else if (id == R.id.menu_change_color) {
            tv_option.setTextColor(getRandomColor());
        } else if (id == R.id.menu_change_bg) {
            tv_option.setBackgroundColor(getRandomColor());
        }
        return true;
    }

    private void setRandomTime() {
        String desc = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss") + " 这里是菜单显示文本";
        tv_option.setText(desc);
    }

    private int[] mColorArray = {
            Color.BLACK, Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN,
            Color.BLUE, Color.CYAN, Color.MAGENTA, Color.GRAY, Color.DKGRAY
    };
    private int getRandomColor() {
        int random = (int) (Math.random()*10 % 10);
        return mColorArray[random];
    }

}

