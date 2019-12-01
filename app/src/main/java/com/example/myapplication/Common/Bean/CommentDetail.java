package com.example.myapplication.Common.Bean;

import com.example.myapplication.ResponseModel.User;

public class CommentDetail {
    private Comment comment;
    private User user;

    public CommentDetail() {
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CommentDetail(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    @Override
    public String toString() {
        return "CommentDetail{" +
                "comment=" + comment +
                ", user=" + user +
                '}';
    }
}
