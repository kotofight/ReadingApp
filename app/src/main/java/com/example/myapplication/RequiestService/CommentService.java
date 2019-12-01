package com.example.myapplication.RequiestService;

import com.example.myapplication.Common.Bean.Comment;
import com.example.myapplication.Common.Bean.CommentDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CommentService {
    @POST("addComment")
    Call<ResponseRec> addComment(@Body Comment comment);
    @GET("getComments")
    Call<ResponseRec<List<CommentDetail>>> getComments();
}
