package com.example.tinder.ui.message.persenter;

import com.example.tinder.http.ApiService;
import com.example.tinder.http.HttpUtils;
import com.example.tinder.http.ResponseListener;
import com.example.tinder.mvp.BaseModel;
import com.example.tinder.mvp.BasePersenter;
import com.example.tinder.ui.message.model.MessageFgModel;
import com.example.tinder.ui.message.view.MessageFgView;
import com.example.tinder.utils.Utils;

public class MessageFgPersenter extends BasePersenter<MessageFgView> {

    public void getMessageList(String token){
        HttpUtils.sendHttp(HttpUtils.createApi(ApiService.class).getMessageList(token), new ResponseListener<BaseModel<MessageFgModel>>() {
            @Override
            public void onSuccess(BaseModel<MessageFgModel> data) {
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
