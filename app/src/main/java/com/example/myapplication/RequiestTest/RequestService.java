package com.example.myapplication.RequiestTest;

import com.example.myapplication.ResponseModel.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RequestService {

    @POST("toLogin")
    @FormUrlEncoded
    Call<ResponseRec<User>> getCall(@Field("username") String account, @Field("pswd")String pswd);
}
