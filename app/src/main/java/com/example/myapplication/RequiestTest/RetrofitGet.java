package com.example.myapplication.RequiestTest;

import com.example.myapplication.MyConfig;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGet {

    private static String url = MyConfig.url;

    public static Retrofit getRetrofit()
    {
        final OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(10*1000, TimeUnit.SECONDS)
                .readTimeout(10*1000, TimeUnit.SECONDS)
                .writeTimeout(10*1000, TimeUnit.SECONDS).build();
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit;

    }
}
