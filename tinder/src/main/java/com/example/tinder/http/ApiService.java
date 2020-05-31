package com.example.tinder.http;

import com.example.tinder.ui.main.model.MainModel;
import com.example.tinder.ui.main.model.UserCenterModel;
import com.example.tinder.ui.main.model.UserDetailsModel;
import com.example.tinder.mvp.BaseModel;
import com.example.tinder.ui.message.model.FeedFgModel;
import com.example.tinder.ui.message.model.MessageFgModel;
import com.example.tinder.ui.message.model.SendMessageModel;
import com.example.tinder.ui.user.Model.LoginModel;
import com.example.tinder.ui.user.Model.RegisterModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    //http://101.132.114.122:8099/user/register
    @FormUrlEncoded
    @POST("/user/register")
    Observable<BaseModel<RegisterModel>> getRegister(@Field("user_type") int userType,
                                                     @Field("photo") String photo,
                                                     @Field("name") String name,
                                                     @Field("email") String email,
                                                     @Field("password") String pass);

    @FormUrlEncoded
    @POST("/user/login")
    Observable<BaseModel<LoginModel>> getLogin(@Field("user_type") int userType,
                                               @Field("email") String email,
                                               @Field("password") String pass);

    @GET("/user/public_user_list")
    Observable<BaseModel<List<MainModel>>> getPublicList(@Query("token") String token,
                                                         @Query("page") int page,
                                                         @Query("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("/home/user_info")
    Observable<BaseModel<UserDetailsModel>> getDetails(@Field("user_id") int userId,
                                                       @Field("token") String token);

    @FormUrlEncoded
    @POST("/user/operation")
    Observable<BaseModel<List<MainModel>>> getAction(@Field("user_id") int userId,
                                                     @Field("token") String token,
                                                     @Field("type") int type);

    @GET("/user/personal")
    Observable<BaseModel<UserCenterModel>> getUserInfo(@Query("token") String token);

    @FormUrlEncoded
    @POST("/user/user_save")
    Observable<BaseModel<UserCenterModel>> setUserInfo(@FieldMap HashMap<String,Object> permter);

    @GET("/message/message_list")
    Observable<BaseModel<MessageFgModel>> getMessageList(@Query("token") String token);

    @GET("/message/collect_list")
    Observable<BaseModel<List<FeedFgModel>>> getFeedList(@Query("token") String token);

    @Multipart
    @POST("/upload")
    Observable<BaseModel> uploadImage(@Part MultipartBody.Part image);

    @GET("/message/message")
    Observable<BaseModel<SendMessageModel>> getMessages(@Query("token") String token,
                                                       @Query("user_id") String userId);

}
