package com.example.myapplication.RequiestService;

import com.bifan.txtreaderlib.Spider.BookBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {
        @GET("search")
        Call<ResponseRec<List<BookBean>>> getCall(@Query("fileName") String bookName);
        @GET("getLastBooks")
        Call<ResponseRec<List<BookBean>>> getLastBooks();
}
