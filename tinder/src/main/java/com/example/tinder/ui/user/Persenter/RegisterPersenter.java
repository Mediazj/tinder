package com.example.tinder.ui.user.Persenter;

import com.example.tinder.http.ActionString;
import com.example.tinder.http.ApiService;
import com.example.tinder.http.HttpUtils;
import com.example.tinder.http.ResponseListener;
import com.example.tinder.mvp.BaseModel;
import com.example.tinder.mvp.BasePersenter;
import com.example.tinder.ui.user.Model.RegisterModel;
import com.example.tinder.ui.user.View.RegisterView;
import com.example.tinder.utils.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.tinder.http.ActionString.UPLOAD_IMAGE;

public class RegisterPersenter extends BasePersenter<RegisterView> {

    public void sendRegister(String photo, String name, String email, String pass) {
        HttpUtils.sendHttp(HttpUtils.createApi(ApiService.class).getRegister(1, photo, name, email, pass),
                new ResponseListener<BaseModel<RegisterModel>>() {
                    @Override
                    public void onSuccess(BaseModel<RegisterModel> data) {
                        if (!Utils.isEmpty(data)) {
                            if (data.getCode() == 200) {
                                RegisterModel model = data.getData();
                                if (getmBaseView() != null) {
                                    getmBaseView().setData(model.getToken(), ActionString.GET_PUBLIC_LIST);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFail(String string) {
                        if (getmBaseView() != null) {
                            getmBaseView().error(string);
                        }
                    }
                });
    }

    public void uploadImage(String url) {
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
