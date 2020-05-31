package com.example.tinder.ui.message.persenter;

import com.example.tinder.http.ApiService;
import com.example.tinder.http.HttpUtils;
import com.example.tinder.http.ResponseListener;
import com.example.tinder.mvp.BaseModel;
import com.example.tinder.mvp.BasePersenter;
import com.example.tinder.ui.message.model.FeedFgModel;
import com.example.tinder.ui.message.view.FeedFgView;
import com.example.tinder.utils.Utils;

import java.util.List;

public class FeedFgPersenter extends BasePersenter<FeedFgView> {

    public void getFeedList(String token){
        HttpUtils.sendHttp(HttpUtils.createApi(ApiService.class).getFeedList(token), new ResponseListener<BaseModel<List<FeedFgModel>>>() {
            @Override
            public void onSuccess(BaseModel<List<FeedFgModel>> data) {
                if(!Utils.isEmpty(data)){
                    if(data.getCode()==200){
                        if(getmBaseView()!=null){
                            getmBaseView().setData(data.getData(),"");
                        }
                    }
                }
            }

            @Override
            public void onFail(String string) {
                if(getmBaseView()!=null){
                    getmBaseView().error("error"+string);
                }
            }
        });
    }
}
