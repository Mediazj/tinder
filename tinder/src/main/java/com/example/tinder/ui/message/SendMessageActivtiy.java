package com.example.tinder.ui.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tinder.R;
import com.example.tinder.http.HttpUtils;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.ui.message.adapter.SendAdapter;
import com.example.tinder.ui.message.model.SendMessageModel;
import com.example.tinder.ui.message.persenter.SendMessagePersenter;
import com.example.tinder.ui.message.view.SendMessageView;
import com.example.tinder.utils.ImageLoader;
import com.example.tinder.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SendMessageActivtiy extends BaseActivity<SendMessageView, SendMessagePersenter> implements SendMessageView, ViewTreeObserver.OnGlobalLayoutListener {

    private ImageView ivBack;
    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvSend;
    private EditText etMessage;
    private RecyclerView rvMessages;
    private LinearLayout llMessage;
    private View view;
    private SendAdapter mSendAdapter;
    private List<SendMessageModel.Messages> list = new ArrayList<>();
    private String userId;

    @Override
    public boolean isRegister() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_message_activtiy;
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
        }
        ivBack = findViewById(R.id.iv_back);
        ivPhoto = findViewById(R.id.iv_photo);
        tvName = findViewById(R.id.tv_name);
        tvSend = findViewById(R.id.tv_send);
        rvMessages = findViewById(R.id.rv_messages);
        llMessage = findViewById(R.id.ll_message);
        etMessage = findViewById(R.id.et_message);

        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        tvSend.setOnClickListener(view -> {
            SendMessageModel.Messages messages = new SendMessageModel.Messages();
            messages.setId(Integer.parseInt(userId));
            messages.setMessage(etMessage.getText().toString().trim());
            messages.setType(2);
            list.add(messages);
            mSendAdapter.notifyDataSetChanged();
            etMessage.setText("");
            Utils.hideAllInputMethod(this);
        });

        ivBack.setOnClickListener(view->{
            finish();
        });

    }

    @Override
    public void initData() {
        view = getWindow().getDecorView();
        view.getViewTreeObserver().addOnGlobalLayoutListener(this);

        getmPersenter().getMessages(getToken(), userId);
    }

    @Override
    protected SendMessagePersenter createPersenter() {
        return new SendMessagePersenter();
    }

    @Override
    public void setData(Object data, String aciton) {
        if (!Utils.isEmpty(data)) {
            SendMessageModel model=(SendMessageModel) data;
            tvName.setText(model.getName());
            ImageLoader.setImageCircle(this,model.getPhoto(),ivPhoto);
            list=model.getMessages();
            if (mSendAdapter == null) {
                mSendAdapter = new SendAdapter(list);
                rvMessages.setAdapter(mSendAdapter);
            } else {
                mSendAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int ht = view.getHeight();
        int ag = ht - rect.bottom;
        if (ag != 0) {
            llMessage.setPadding(0, 0, 0, ag);
        } else {
            llMessage.setPadding(0, 0, 0, 0);
        }
    }

    public void finish(){
        super.finish();
    }
}
