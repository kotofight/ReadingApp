package com.example.myapplication.Common.Bean;


import java.io.Serializable;

public class Users implements Serializable{
    private int userPicPath;//个人头像
    private String userName;// 发布人
    private String datetime;// 发布时间
    private String address;//发布地址
    private int  look;//关注图片
    private String content;// 趣事内容
    private int picPath;// 趣事图片路径
    private int forwardImg;// 转发图片
    private int  forward;// 转发数量
    private int commentImg;// 评论图片
    private int  comment;// 评论数量
    private int thumbUpImg;// 点赞图片
    private int  thumbUp;// 点赞数量

    public Users() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Users(int userPicPath, String userName,int look, String content, String datetime, String address, int picPath, int forwardImg, int forward, int commentImg, int comment, int thumbUpImg, int thumbUp) {
        //super();
        this.userPicPath = userPicPath;
        this.userName = userName;
        this.look = look;
        this.content = content;
        this.datetime = datetime;
        this.address = address;
        this.picPath = picPath;
        this.forwardImg = forwardImg;
        this.forward = forward;
        this.commentImg = commentImg;
        this.comment = comment;
        this.thumbUpImg = thumbUpImg;
        this.thumbUp = thumbUp;
    }

    public int getUserPicPath() {
        return userPicPath;
    }

    public void setUserPicPath(int userPicPath) {
        this.userPicPath = userPicPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public int getLook() {
        return look;
    }

    public void setLook(int look) {
        this.look = look;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPicPath() {
        return picPath;
    }

    public void setPicPath(int picPath) {
        this.picPath = picPath;
    }

    public int getForwardImg() {
        return forwardImg;
    }

    public void setForwardImg(int forwardImg) {
        this.forwardImg = forwardImg;
    }

    public int getForward() {
        return forward;
    }

    public void setForward(int forward) {
        this.forward = forward;
    }

    public int getCommentImg() {
        return commentImg;
    }

    public void setCommentImg(int commentImg) {
        this.commentImg = commentImg;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getThumbUpImg() {
        return thumbUpImg;
    }

    public void setThumbUpImg(int thumbUpImg) {
        this.thumbUpImg = thumbUpImg;
    }

    public int getThumbUp() {
        return thumbUp;
    }

    public void setThumbUp(int thumbUp) {
        this.thumbUp = thumbUp;
    }
/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userPicPath);
        dest.writeString(userName);
        dest.writeInt(look);
        dest.writeString(content);
        dest.writeString(datetime);
        dest.writeString(address);
        dest.writeInt(picPath);
        dest.writeInt(forwardImg);
        dest.writeInt(forward);
        dest.writeInt(commentImg);
        dest.writeInt(comment);
        dest.writeInt(thumbUpImg);
        dest.writeInt(thumbUp);
    }
    public static final Parcelable.Creator<Users> CREATOR = new Creator<Users>() {

        @Override
        public Users createFromParcel(Parcel source) {
            // 必须按成员变量的顺序读取数据，不然会出现获取数据报错
            Users users = new Users();
            users.setUserPicPath(source.readInt());
            users.setUserName(source.readString());
            users.setDatetime(source.readString());
            users.setAddress(source.readString());
            users.setLook(source.readInt());
            users.setContent(source.readString());
            users.setPicPath(source.readInt());
            users.setForwardImg(source.readInt());
            users.setForward(source.readInt());
            users.setCommentImg(source.readInt());
            users.setComment(source.readInt());
            users.setThumbUpImg(source.readInt());
            users.setThumbUp(source.readInt());
            return users;
        }

        @Override
        public Users[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Users[size];
        }
    };*/
}
