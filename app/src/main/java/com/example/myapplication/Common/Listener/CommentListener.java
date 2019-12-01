package com.example.myapplication.Common.Listener;

import com.example.myapplication.Common.Bean.CommentDetail;

import java.util.List;

public interface CommentListener {
    void checkStatus(String msg);
    void getComments(List<CommentDetail> comments);
}
