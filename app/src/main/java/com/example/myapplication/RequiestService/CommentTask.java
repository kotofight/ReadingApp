package com.example.myapplication.RequiestService;

import android.util.Log;

import com.example.myapplication.Common.Bean.Comment;
import com.example.myapplication.Common.Bean.CommentDetail;
import com.example.myapplication.Common.Listener.CommentListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentTask {
    public void addComment(Comment comment, final CommentListener listener){
        Retrofit retrofit = RetrofitGet.getRetrofit();
        retrofit.create(CommentService.class).addComment(comment).enqueue(new Callback<ResponseRec>() {
            @Override
            public void onResponse(Call<ResponseRec> call, Response<ResponseRec> response) {
                ResponseRec responseRec = null;
                responseRec = response.body();
                Log.i("消息","请求响应成功"+responseRec.getMsg());
                if(null == responseRec){
                    listener.checkStatus("请求无响应");
                }else {
                    listener.checkStatus(responseRec.getMsg());
                }
            }
            @Override
            public void onFailure(Call<ResponseRec> call, Throwable t) {
                listener.checkStatus("数据请求失败");
                Log.i("消息","请求响应失败");
            }
        });
    }
    public void getComments(final List<CommentDetail> comments, final CommentListener listener){
        Retrofit retrofit = RetrofitGet.getRetrofit();
        retrofit.create(CommentService.class).getComments().enqueue(new Callback<ResponseRec<List<CommentDetail>>>() {
            @Override
            public void onResponse(Call<ResponseRec<List<CommentDetail>>> call, Response<ResponseRec<List<CommentDetail>>> response) {

                if(response!=null){
                    List<CommentDetail> list = response.body().getData();
                    comments.addAll(list);
                    listener.getComments(list);
                    Log.i("消息","访问成功 "+list.toString());
                }
                Log.i("消息","访问失败response=null");
            }
            @Override
            public void onFailure(Call<ResponseRec<List<CommentDetail>>> call, Throwable t) {
                Log.i("消息","Comments访问失败");
            }
        });
    }
}
