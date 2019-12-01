package com.example.myapplication.RequiestService;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FileService {
    @POST("uploadBook")
    Call<ResponseRec> upload(@Body RequestBody body);
}
