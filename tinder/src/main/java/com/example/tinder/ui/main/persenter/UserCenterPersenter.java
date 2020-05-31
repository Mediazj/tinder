package com.example.tinder.ui.main.persenter;

import com.example.tinder.http.ApiService;
import com.example.tinder.http.HttpUtils;
import com.example.tinder.http.ResponseListener;
import com.example.tinder.ui.main.View.UserCenterView;
import com.example.tinder.ui.main.model.UserCenterModel;
import com.example.tinder.mvp.BaseModel;
import com.example.tinder.mvp.BasePersenter;
import com.example.tinder.utils.Utils;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.tinder.http.ActionString.GET_USERINFO;
import static com.example.tinder.http.ActionString.SET_USERSAVE;
import static com.example.tinder.http.ActionString.UPLOAD_IMAGE;

public class UserCenterPersenter extends BasePersenter<UserCenterView> {

    public void getUserInfo(String token) {
        HttpUtils.sendHttp(HttpUtils.createApi(ApiService.class).getUserInfo(token), new ResponseListener<BaseModel<UserCenterModel>>() {
            @Override
            public void onSuccess(BaseModel<UserCenterModel> data) {
                if (!Utils.isEmpty(data)) {
                    if (data.getCode() == 200) {
                        if (!Utils.isEmpty(getmBaseView())) {
                            getmBaseView().setData(data.getData(), GET_USERINFO);
                        }
                    }
                }
            }

            @Override
            public void onFail(String string) {
                if (!Utils.isEmpty(getmBaseView())) {
                    getmBaseView().error(string);
                }
            }
        });
    }

    public void setUserInfo(HashMap<String,Object> value) {
        HttpUtils.sendHttp(HttpUtils.createApi(ApiService.class).setUserInfo(value), new ResponseListener<BaseModel<UserCenterModel>>() {
            @Override
            public void onSuccess(BaseModel<UserCenterModel> data) {
                if (!Utils.isEmpty(data)) {
                    if (data.getCode() == 200) {
                        if (!Utils.isEmpty(getmBaseView())) {
                            getmBaseView().setData(data.getData(), SET_USERSAVE);
                        }
                    }
                }
            }

            @Override
            public void onFail(String string) {
                if (!Utils.isEmpty(getmBaseView())) {
                    getmBaseView().error(string);
                }
            }
        });
    }

    public void uploadImage(String url,int code) {
        HttpUtils.sendHttp(HttpUtils.createApi(ApiService.class).uploadImage(getPart(url)), new ResponseListener<BaseModel>() {
            @Override
            public void onSuccess(BaseModel data) {
                if (!Utils.isEmpty(data)) {
                    if (data.getCode() == 200) {
                        if (!Utils.isEmpty(getmBaseView())) {
                            getmBaseView().setData(data.getData(), UPLOAD_IMAGE);
                        }
                    }
                }
            }

            @Override
            public void onFail(String string) {
                if (!Utils.isEmpty(getmBaseView())) {
                    getmBaseView().error(string);
                }
            }
        });
    }

    private MultipartBody.Part getPart(String url) {
        File file = new File(url);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestBody);
    }
}
