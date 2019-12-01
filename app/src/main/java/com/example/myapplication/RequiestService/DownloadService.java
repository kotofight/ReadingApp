package com.example.myapplication.RequiestService;

import com.bifan.txtreaderlib.Spider.BookBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DownloadService {
    @POST("downloadBook")
    Call<ResponseBody> download(@Body BookBean bookBean);
}
