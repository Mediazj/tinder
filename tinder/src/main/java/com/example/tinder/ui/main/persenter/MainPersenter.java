package com.example.tinder.ui.main.persenter;

import com.example.tinder.http.ActionString;
import com.example.tinder.http.ApiService;
import com.example.tinder.http.HttpUtils;
import com.example.tinder.http.ResponseListener;
import com.example.tinder.ui.main.View.MainView;
import com.example.tinder.ui.main.model.MainModel;
import com.example.tinder.mvp.BaseModel;
import com.example.tinder.mvp.BasePersenter;
import com.example.tinder.utils.Utils;

import java.util.List;

public class MainPersenter extends BasePersenter<MainView> {

    public void getPublicList(String token, int page, int pageSize) {
        HttpUtils.sendHttp(HttpUtils.createApi(ApiService.class).getPublicList(token, page, pageSize),
                new ResponseListener<BaseModel<List<MainModel>>>() {
                    @Override
                    public void onSuccess(BaseModel<List<MainModel>> data) {
                        if (!Utils.isEmpty(data)) {
                            if (data.getCode() == 200) {
                                if (getmBaseView() != null) {
                                    getmBaseView().setData(data.getData(),ActionString.GET_PUBLIC_LIST);
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

    public void getAction(String token, int userId, final int type) {
        HttpUtils.sendHttp(HttpUtils.createApi(ApiService.class).getAction(userId,token,type),
                new ResponseListener<BaseModel<List<MainModel>>>() {
                    @Override
                    public void onSuccess(BaseModel<List<MainModel>> data) {
                        if (!Utils.isEmpty(data)) {
                            if (data.getCode() == 200) {
                                if (getmBaseView() != null) {
                                    getmBaseView().acType(type);
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
}

