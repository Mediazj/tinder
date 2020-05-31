package com.example.tinder.ui.user.Persenter;

import com.example.tinder.http.ActionString;
import com.example.tinder.http.ApiService;
import com.example.tinder.http.HttpUtils;
import com.example.tinder.http.ResponseListener;
import com.example.tinder.mvp.BaseModel;
import com.example.tinder.mvp.BasePersenter;
import com.example.tinder.mvp.BaseView;
import com.example.tinder.ui.user.Model.LoginModel;
import com.example.tinder.ui.user.View.LoginView;
import com.example.tinder.utils.Utils;

public class LoginPersenter extends BasePersenter<LoginView> {

    public void sendLogin(String email, String pass) {
        HttpUtils.sendHttp(HttpUtils.createApi(ApiService.class).getLogin(1, email, pass),
                new ResponseListener<BaseModel<LoginModel>>() {
            @Override
            public void onSuccess(BaseModel<LoginModel> data) {
                if(!Utils.isEmpty(data)){
                    if(data.getCode()==200){
                        LoginModel loginModel = data.getData();
                        if(getmBaseView()!=null){
                            getmBaseView().setData(loginModel.getToken(), ActionString.GET_PUBLIC_LIST);
                        }
                    }
                }
            }

            @Override
            public void onFail(String string) {
                if(getmBaseView()!=null){
                    getmBaseView().error(string);
                }
            }
        });
    }
}
