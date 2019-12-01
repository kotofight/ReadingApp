package com.example.myapplication.RequiestService;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CheckEmail {
    @POST("checkEmail")
    @FormUrlEncoded
    Call<ResponseRec<String>> checkEmail(@Field("email")String email);
}
