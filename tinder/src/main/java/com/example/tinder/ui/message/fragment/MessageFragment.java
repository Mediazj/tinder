package com.example.tinder.ui.message.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinder.R;
import com.example.tinder.ui.base.BaseFragment;
import com.example.tinder.ui.message.SendMessageActivtiy;
import com.example.tinder.ui.message.adapter.UserMessageAdapter;
import com.example.tinder.ui.message.model.MessageFgModel;
import com.example.tinder.ui.message.persenter.MessageFgPersenter;
import com.example.tinder.ui.message.view.MessageFgView;
import com.example.tinder.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends BaseFragment<MessageFgView, MessageFgPersenter> implements MessageFgView {

    private RecyclerView rvUserMessage;
    private UserMessageAdapter adapter;
    List<MessageFgModel.UserNew> userNewList;
    List<MessageFgModel.UserOld> userOldList;

    public MessageFragment(String title) {
        super(title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView(View view) {
        rvUserMessage = view.findViewById(R.id.rv_user_message);
        rvUserMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        getmPersenter().getMessageList("f2e0c88f-7d32-3464-9cc5");
    }

    @Override
    protected MessageFgPersenter createPersenter() {
        return new MessageFgPersenter();
    }

    @Override
    public void setData(Object data, String aciton) {
        userNewList = new ArrayList<>();
        userOldList = new ArrayList<>();
        if (!Utils.isEmpty(data)) {
            MessageFgModel model = (MessageFgModel) data;
            userNewList = model.getNew_user_list();
            userOldList = model.getOld_user_list();
            if (adapter == null) {
                adapter = new UserMessageAdapter(userNewList, userOldList);
                rvUserMessage.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
        adapter.setOnItemListener(data1 ->
                {
                    if(!Utils.isEmpty(data1)){
                        MessageFgModel.UserOld model=(MessageFgModel.UserOld)data1;
                        Intent intent = new Intent();
                        intent.putExtra("userId",model.getId()+"");
                        intent.setClass(getActivity(), SendMessageActivtiy.class);
                        startActivity(intent);
                    }

                }
        );
    }

    @Override
    public void error(String msg) {

    }
}
