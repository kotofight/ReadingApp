package com.example.myapplication.Common.Bean;

public class Comment {
    int id;
    int userid;
    String time;
    String content;
    String picture;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userid=" + userid +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
