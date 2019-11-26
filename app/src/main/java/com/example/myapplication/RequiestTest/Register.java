package com.example.myapplication.RequiestTest;

import com.example.myapplication.ResponseModel.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Register {

    @POST("/register")
    @FormUrlEncoded
    Call<ResponseRec<User>> doRegister(@Field("userName") String userName, @Field("password") String password, @Field("email") String email, @Field("telephone") String telephone);
}
