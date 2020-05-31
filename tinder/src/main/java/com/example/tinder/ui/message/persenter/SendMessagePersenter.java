package com.example.tinder.ui.message.persenter;

import com.example.tinder.http.ApiService;
import com.example.tinder.http.HttpUtils;
import com.example.tinder.http.ResponseListener;
import com.example.tinder.mvp.BaseModel;
import com.example.tinder.mvp.BasePersenter;
import com.example.tinder.ui.message.model.SendMessageModel;
import com.example.tinder.ui.message.view.SendMessageView;
import com.example.tinder.utils.Utils;

public class SendMessagePersenter extends BasePersenter<SendMessageView> {

    public void getMessages(String token,String userId){
        HttpUtils.sendHttp(HttpUtils.createApi(ApiService.class).getMessages(token, userId), new ResponseListener<BaseModel<SendMessageModel>>() {
            @Override
            public void onSuccess(BaseModel<SendMessageModel> data) {
                if(!Utils.isEmpty(data)){
                    if(data.getCode()==200){
                        if(getmBaseView() !=null){
                            getmBaseView().setData(data.getData(),"");
                        }
                    }
                }
            }

            @Override
            public void onFail(String string) {
                if(getmBaseView() !=null){
                    getmBaseView().error("error"+string);
                }
            }
        });
    }
}
