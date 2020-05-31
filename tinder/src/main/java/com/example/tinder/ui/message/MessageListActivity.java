package com.example.tinder.ui.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tinder.R;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.ui.base.BaseFragment;
import com.example.tinder.ui.message.adapter.FragmentAdapter;
import com.example.tinder.ui.message.fragment.FeedFragment;
import com.example.tinder.ui.message.fragment.MessageFragment;
import com.example.tinder.ui.message.model.MessageListModel;
import com.example.tinder.ui.message.persenter.MessageListPersenter;
import com.example.tinder.ui.message.view.MessageListView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends BaseActivity<MessageListView, MessageListPersenter> implements MessageListView , View.OnClickListener {

    private TabLayout tabTitle;
    private ViewPager vpFragment;
    private ImageView ivProfile;

    @Override
    public boolean isRegister() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    public void initView() {
        tabTitle = findViewById(R.id.tab_title);
        vpFragment = findViewById(R.id.vp_fragment);
        ivProfile=findViewById(R.id.iv_profile);
        ivProfile.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tabTitle.addTab(tabTitle.newTab());
        tabTitle.addTab(tabTitle.newTab());
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new MessageFragment("Message"));
        fragments.add(new FeedFragment("Feed"));
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        vpFragment.setAdapter(fragmentAdapter);
        tabTitle.setupWithViewPager(vpFragment);
    }

    @Override
    protected MessageListPersenter createPersenter() {
        return new MessageListPersenter();
    }

    @Override
    public void setData(Object data, String aciton) {

    }

    @Override
    public void error(String msg) {

    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.anim_left,R.anim.anim_right_out);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_profile){
            finish();
        }
    }
}
