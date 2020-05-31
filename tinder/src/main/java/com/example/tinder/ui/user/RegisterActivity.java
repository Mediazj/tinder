package com.example.tinder.ui.user;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.tinder.http.ActionString;
import com.example.tinder.ui.main.MainActivity;
import com.example.tinder.R;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.ui.user.Persenter.RegisterPersenter;
import com.example.tinder.ui.user.View.RegisterView;
import com.example.tinder.utils.SharedPreferencesUtils;
import com.example.tinder.utils.Utils;

import static com.example.tinder.http.ActionString.GET_PUBLIC_LIST;
import static com.example.tinder.http.ActionString.UPLOAD_IMAGE;

public class RegisterActivity extends BaseActivity<RegisterView, RegisterPersenter> implements TextWatcher, View.OnClickListener, RegisterView {

    private ImageView ivPhoto;
    private EditText etName;
    private EditText etEmail;
    private EditText etPass;
    private Button btRegister;
    private TextView tvGologin;

    private String name;
    private String email;
    private String pass;
    private String photo;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] permStr = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    public boolean isRegister() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    public void initView() {
        ivPhoto = findViewById(R.id.iv_photo);
        etEmail = findViewById(R.id.et_email);
        etName = findViewById(R.id.et_name);
        etPass = findViewById(R.id.et_pass);
        btRegister = findViewById(R.id.bt_register);
        tvGologin = findViewById(R.id.tv_gologin);

        etPass.addTextChangedListener(this);
        etName.addTextChangedListener(this);
        etPass.addTextChangedListener(this);
        ivPhoto.setOnClickListener(this);
        tvGologin.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        initData();
    }

    @Override
    public void initData() {
        int hasPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permStr, REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected RegisterPersenter createPersenter() {
        return new RegisterPersenter();
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Uri data1 = data.getData();
                ivPhoto.setImageURI(data1);
                getmPersenter().uploadImage(Utils.getDataUrl(this, data1));
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        name = getEditText(etName);
        email = getEditText(etEmail);
        pass = getEditText(etPass);
        if (name.length() > 0 && email.length() > 0 && pass.length() >= 6) {
            btRegister.setBackgroundResource(R.drawable.bg_btn_register);
        } else {
            btRegister.setBackgroundResource(R.drawable.bg_btn_register_not);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                if (TextUtils.isEmpty(photo)) {
                    showToast(getString(R.string.toast_input_photo));
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    showToast(getString(R.string.toast_input_name));
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    showToast(getString(R.string.toast_input_email));
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    showToast(getString(R.string.toast_input_pass));
                    return;
                }

                getmPersenter().sendRegister(photo, name, email, pass);
                break;
            case R.id.tv_gologin:
                startIntent(LoginActivity.class);
                break;
            case R.id.iv_photo:
                selectPhoto();
            default:
                break;
        }
    }

    @Override
    public void setData(Object data, String action) {
        if (action.equals(UPLOAD_IMAGE)) {
            photo = (String) data;
            return;
        }
        if (action.equals(GET_PUBLIC_LIST)) {
            if (data != null) {
                String token = (String) data;
                SharedPreferencesUtils.saveToken(this, "token", token);
                startIntent(MainActivity.class, true);
            }
        }
    }

    @Override
    public void error(String msg) {
        Log.e("error", "" + msg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPhoto();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(this)
                            .setMessage("操作时需要如下权限：")
                            .setPositiveButton("是", (dialog1, which) ->
                                    ActivityCompat.requestPermissions(this, permStr, REQUEST_EXTERNAL_STORAGE))
                            .setNegativeButton("否", null)
                            .create()
                            .show();
                }
            }
        }
    }
}
