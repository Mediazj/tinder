package com.example.tinder.ui.user;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tinder.http.ActionString;
import com.example.tinder.ui.main.MainActivity;
import com.example.tinder.R;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.ui.user.Persenter.LoginPersenter;
import com.example.tinder.ui.user.View.LoginView;
import com.example.tinder.utils.SharedPreferencesUtils;

public class LoginActivity extends BaseActivity<LoginView, LoginPersenter> implements TextWatcher, View.OnClickListener, LoginView {

    private EditText etName;
    private EditText etPass;
    private Button btLogin;
    private TextView tvBackRegister;

    private String email;
    private String pass;

    @Override
    public boolean isRegister() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    public void initView() {
        etName = findViewById(R.id.et_name);
        etPass = findViewById(R.id.et_pass);
        btLogin = findViewById(R.id.bt_login);
        tvBackRegister = findViewById(R.id.tv_back_register);

        etName.addTextChangedListener(this);
        etPass.addTextChangedListener(this);
        btLogin.setOnClickListener(this);
        tvBackRegister.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected LoginPersenter createPersenter() {
        return new LoginPersenter();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        email = getEditText(etName);
        pass = getEditText(etPass);
        if (email.length() > 0 && pass.length() >= 6) {
            btLogin.setBackgroundResource(R.drawable.bg_btn_register);
        } else {
            btLogin.setBackgroundResource(R.drawable.bg_btn_register_not);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                if (TextUtils.isEmpty(email)) {
                    showToast(getString(R.string.toast_input_email));
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    showToast(getString(R.string.toast_input_pass));
                    return;
                }
                getmPersenter().sendLogin(email, pass);
                break;
            case R.id.tv_back_register:
                startIntent(RegisterActivity.class);
                break;
            default:
                break;

        }
    }

    @Override
    public void setData(Object data,String action) {
        if(action.equals(ActionString.GET_PUBLIC_LIST)) {
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
}
