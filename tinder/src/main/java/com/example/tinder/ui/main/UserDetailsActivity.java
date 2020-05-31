package com.example.tinder.ui.main;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.tinder.R;
import com.example.tinder.http.ActionString;
import com.example.tinder.ui.main.View.UserDetailsView;
import com.example.tinder.ui.main.adapter.PhotoAdapter;
import com.example.tinder.ui.main.model.UserDetailsModel;
import com.example.tinder.ui.main.persenter.UserDetailsPersenter;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.utils.Utils;

public class UserDetailsActivity extends BaseActivity<UserDetailsView, UserDetailsPersenter>
        implements UserDetailsView, View.OnClickListener {

    private ViewPager vpPhoto;
    private LinearLayout llLine;
    private View[] vLines;
    private int userId;
    private TextView tvName;
    private TextView tvAge;
    private TextView tvWork;
    private TextView tvBio;
    private ImageView ivFinish;
    private ImageView ivPic1;
    private ImageView ivPic2;
    private ImageView ivPic3;


    @Override
    public boolean isRegister() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_details;
    }

    @Override
    public void initView() {
        vpPhoto = findViewById(R.id.vp_photo);
        llLine = findViewById(R.id.ll_line);
        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);
        tvWork = findViewById(R.id.tv_work);
        tvBio = findViewById(R.id.tv_bio);
        ivFinish = findViewById(R.id.iv_finish);
        ivPic1=findViewById(R.id.iv_pic1);
        ivPic2=findViewById(R.id.iv_pic2);
        ivPic3=findViewById(R.id.iv_pic3);
        ivFinish.setOnClickListener(this);
        ivPic1.setOnClickListener(this);
        ivPic2.setOnClickListener(this);
        ivPic3.setOnClickListener(this);
        if (getIntent() != null) {
            userId = getIntent().getIntExtra("userId", 0);
        }
    }

    @Override
    public void initData() {
        if (!Utils.isEmpty(userId)) {
            getmPersenter().getUserDetails(userId, getToken());
        }

        vpPhoto.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectLine(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initLine();
    }

    private void initLine() {
        vLines = new View[3];
        for (int i = 0; i < 3; i++) {
            vLines[i] = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = 10;
            params.weight = 1;
            params.rightMargin = 15;
            params.leftMargin = 15;
            vLines[i].setLayoutParams(params);
            if (i == 0) {
                vLines[i].setBackgroundResource(R.drawable.bg_line);
            } else {
                vLines[i].setBackgroundResource(R.drawable.bg_line_not);
            }
            llLine.addView(vLines[i]);
        }
    }

    private void selectLine(int index) {
        for (int i = 0; i < 3; i++) {
            if (index == i) {
                vLines[i].setBackgroundResource(R.drawable.bg_line);
            } else {
                vLines[i].setBackgroundResource(R.drawable.bg_line_not);
            }
        }
    }

    @Override
    protected UserDetailsPersenter createPersenter() {
        return new UserDetailsPersenter();
    }

    @Override
    public void setData(Object data, String action) {
        if (action.equals(ActionString.GET_PUBLIC_LIST)) {
            if (!Utils.isEmpty(data)) {
                UserDetailsModel model = (UserDetailsModel) data;
                PhotoAdapter adapter = new PhotoAdapter(model.getPhoto());
                vpPhoto.setAdapter(adapter);
                tvName.setText(model.getUser_name());
                tvAge.setText(model.getAge());
                tvWork.setText(model.getWork());
                tvBio.setText(model.getBio());
            }
        }
    }

    @Override
    public void error(String msg) {
        Log.e("error", msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:
                finish();
                break;
            case R.id.iv_pic1:
                finish();
                break;
            case R.id.iv_pic2:
                setAction(1);
                break;
            case R.id.iv_pic3:
                setAction(2);
                break;
            default:
                break;
        }
    }

    private void setAction(int type) {
        if (!Utils.isEmpty(userId)) {
            getmPersenter().getAction(getToken(), userId, type);
        }
    }

    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.anim_bottom_out);
    }

    @Override
    public void acType(int type) {
        switch (type) {
            case 1:
                finish();
                break;
            case 2:
                finish();
                break;
            default:
                break;
        }
    }
}
