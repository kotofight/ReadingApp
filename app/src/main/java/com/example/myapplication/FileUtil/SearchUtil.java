package com.example.myapplication.FileUtil;

import android.util.Log;

import com.bifan.txtreaderlib.Spider.BookBean;
import com.example.myapplication.RequiestTest.ResponseRec;
import com.example.myapplication.RequiestTest.RetrofitGet;
import com.example.myapplication.RequiestTest.SearchService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchUtil {
    public static void searchBaseBook(String bookName,final List<BookBean> list)
    {
        Retrofit retrofit = RetrofitGet.getRetrofit();
        SearchService searchService = retrofit.create(SearchService.class);
        Call<ResponseRec<List<BookBean>>> call = searchService.getCall(bookName);
        call.enqueue(new Callback<ResponseRec<List<BookBean>>>() {
            @Override
            public void onResponse(Call<ResponseRec<List<BookBean>>> call, Response<ResponseRec<List<BookBean>>> response) {

                ResponseRec<List<BookBean>> responseRec= response.body();
                list.addAll(responseRec.getData());
                Log.i("搜索","信息："+responseRec.getMsg()+"  "+responseRec.getStatus());//登陆成功的信息
            }

            @Override
            public void onFailure(Call<ResponseRec<List<BookBean>>> call, Throwable t) {
                Log.e("搜索失败","访问失败"+t.toString()+call.request().toString());

            }
        });
    }
}
