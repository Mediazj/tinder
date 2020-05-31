package com.example.tinder.ui.message.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinder.R;
import com.example.tinder.mvp.BasePersenter;
import com.example.tinder.ui.base.BaseFragment;
import com.example.tinder.ui.message.adapter.UserFeedAdapter;
import com.example.tinder.ui.message.model.FeedFgModel;
import com.example.tinder.ui.message.persenter.FeedFgPersenter;
import com.example.tinder.ui.message.view.FeedFgView;
import com.example.tinder.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends BaseFragment<FeedFgView, FeedFgPersenter> implements FeedFgView {

    private RecyclerView rvFeed;
    private UserFeedAdapter adapter;
    private List<FeedFgModel> userFeedList;

    public FeedFragment(String title) {
        super(title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

    @Override
    protected void initView(View view) {
        rvFeed = view.findViewById(R.id.rv_feed);
        rvFeed.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        getmPersenter().getFeedList("f2e0c88f-7d32-3464-9cc5");
    }
//    f2e0c88f-7d32-3464-9cc5

    @Override
    protected FeedFgPersenter createPersenter() {
        return new FeedFgPersenter();
    }


    @Override
    public void setData(Object data, String aciton) {
        if (!Utils.isEmpty(data)) {
            userFeedList = new ArrayList<>();
            userFeedList=(List<FeedFgModel>) data;
            if (adapter == null) {
                adapter = new UserFeedAdapter(userFeedList);
                rvFeed.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void error(String msg) {

    }
}
