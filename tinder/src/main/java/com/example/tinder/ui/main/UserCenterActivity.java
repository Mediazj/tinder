package com.example.tinder.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tinder.R;
import com.example.tinder.http.ActionString;
import com.example.tinder.ui.main.View.UserCenterView;
import com.example.tinder.ui.main.model.UserCenterModel;
import com.example.tinder.ui.main.persenter.UserCenterPersenter;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.ui.user.LoginActivity;
import com.example.tinder.utils.ImageLoader;
import com.example.tinder.utils.SharedPreferencesUtils;
import com.example.tinder.utils.Utils;

import java.util.HashMap;

public class UserCenterActivity extends BaseActivity<UserCenterView, UserCenterPersenter>
        implements UserCenterView, View.OnClickListener {

    private TextView tvCancel;
    private TextView tvSave;
    private TextView tvLogout;
    private ImageView ivPhotoOne;
    private ImageView ivPhotoTwo;
    private ImageView ivPhotoThree;
    private EditText etName;
    private EditText etProfes;
    private EditText etAge;
    private EditText etBio;
    private TextView tvMin;
    private TextView tvMax;
    private SeekBar sbMin;
    private SeekBar sbMax;
    private UserCenterModel model;
    String photo1;
    String photo2;
    String photo3;

    @Override
    public boolean isRegister() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    public void initView() {
        tvCancel = findViewById(R.id.tv_cancel);
        tvSave = findViewById(R.id.tv_save);
        tvLogout = findViewById(R.id.tv_logout);

        ivPhotoOne = findViewById(R.id.iv_photo_one);
        ivPhotoTwo = findViewById(R.id.iv_photo_two);
        ivPhotoThree = findViewById(R.id.iv_photo_three);

        etName = findViewById(R.id.et_name);
        etProfes = findViewById(R.id.et_profess);
        etAge = findViewById(R.id.et_age);
        etBio = findViewById(R.id.et_bio);

        tvMin = findViewById(R.id.tv_min);
        tvMax = findViewById(R.id.tv_max);
        sbMin = findViewById(R.id.sb_min);
        sbMax = findViewById(R.id.sb_max);

        tvCancel.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        ivPhotoOne.setOnClickListener(this);
        ivPhotoTwo.setOnClickListener(this);
        ivPhotoThree.setOnClickListener(this);
        sbMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvMin.setText("Min:" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvMax.setText("Max:" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    public void initData() {
        getmPersenter().getUserInfo("f2e0c88f-7d32-3464-9cc5");
    }

    @Override
    protected UserCenterPersenter createPersenter() {
        return new UserCenterPersenter();
    }

    @Override
    public void setData(Object data, String action) {
        if (ActionString.GET_USERINFO.equals(action)) {
            if (!Utils.isEmpty(data)) {
                model = (UserCenterModel) data;
                if (!Utils.isEmpty(model.getPhoto1())) {
                    ImageLoader.setImage(this, model.getPhoto1(), ivPhotoOne);
                }
                if (!Utils.isEmpty(model.getPhoto2())) {
                    ImageLoader.setImage(this, model.getPhoto2(), ivPhotoTwo);
                }
                if (!Utils.isEmpty(model.getPhoto3())) {
                    ImageLoader.setImage(this, model.getPhoto3(), ivPhotoThree);
                }
                if (!Utils.isEmpty(model.getUser_name())) {
                    etName.setText(model.getUser_name());
                }
                if (!Utils.isEmpty(model.getWork())) {
                    etProfes.setText(model.getWork());
                }
                if (!Utils.isEmpty(model.getAge())) {
                    etAge.setText(model.getAge());
                }
                if (!Utils.isEmpty(model.getBio())) {
                    etBio.setText(model.getBio());
                }
                if (!Utils.isEmpty(model.getAge_min())) {
                    sbMin.setProgress(model.getAge_min());
                    tvMin.setText("Min:" + model.getAge_min());
                }
                if (!Utils.isEmpty(model.getAge_max())) {
                    sbMax.setProgress(model.getAge_max());
                    tvMax.setText("Max:" + model.getAge_max());
                }

            }
        }
    }

    @Override
    public void error(String msg) {
        Log.e("error", msg);
    }

    private void selectPhoto(int code) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent, code);
        }else{
            showToast("没有相册");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Utils.isEmpty(data)) {
            return;
        }
        Uri data1 = null;
        if (resultCode == RESULT_OK) {
            data1 = data.getData();
        }
        if (requestCode == 100) {
            ivPhotoOne.setImageURI(data1);
            getmPersenter().uploadImage(Utils.getDataUrl(this, data1),1);
        } else if (requestCode == 101) {
            ivPhotoTwo.setImageURI(data1);
            getmPersenter().uploadImage(Utils.getDataUrl(this, data1),2);
        } else if (requestCode == 102) {
            ivPhotoThree.setImageURI(data1);
            getmPersenter().uploadImage(Utils.getDataUrl(this, data1),3);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_photo_one:
                selectPhoto(100);
                break;
            case R.id.iv_photo_two:
                selectPhoto(101);
                break;
            case R.id.iv_photo_three:
                selectPhoto(102);
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_logout:
                SharedPreferencesUtils.saveToken(this, "token", null);
                startIntent(LoginActivity.class);
                finish();
                break;
            case R.id.tv_save:
                HashMap<String, Object> map = new HashMap<>();
                map.put("token", getToken());
                if (!Utils.isEmpty(photo1)) {
                    map.put("photo1", photo1);
                }
                if (!Utils.isEmpty(photo2)) {
                    map.put("photo2", photo2);
                }
                if (!Utils.isEmpty(photo3)) {
                    map.put("photo3", photo3);
                }
                String name = getEditText(etName);
                String age = getEditText(etAge);
                String work = getEditText(etProfes);
                String bio = getEditText(etBio);
                if (!Utils.isEmpty(name)) {
                    map.put("user_name", name);
                }
                if (!Utils.isEmpty(age)) {
                    map.put("age", age);
                }
                if (!Utils.isEmpty(work)) {
                    map.put("work", work);
                }
                if (!Utils.isEmpty(bio)) {
                    map.put("bio", bio);
                }
                int ageMin = sbMin.getProgress();
                if (!Utils.isEmpty(ageMin)) {
                    map.put("age_min", ageMin);
                }
                int ageMax = sbMax.getProgress();
                if (!Utils.isEmpty(ageMax)) {
                    map.put("age_max", ageMax);
                }
                getmPersenter().setUserInfo(map);
                break;
            default:
                break;
        }
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_bottom_not, R.anim.anim_bottom_out);
    }

    @Override
    public void imageType(Object data, int code) {
        if (!Utils.isEmpty(data)) {
            switch (code) {
                case 1:
                    photo1=(String)data;
                    break;
                case 2:
                    photo2=(String)data;
                    break;
                case 3:
                    photo3=(String)data;
                    break;
                default:
                    break;
            }
        }
    }
}
