package com.example.myapplication.SQLiteDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ResponseModel.User;

public class DBOperation {
    private final MyDBOpenHelper helper;

    //构造方法：初始化成员变量helper
    public DBOperation(Context context)
    {
        helper = new MyDBOpenHelper(context);
    }

    public void add(User user)
    {
        SQLiteDatabase user_db = helper.getWritableDatabase();
        user_db.execSQL("insert into user_info (userID,userName,userPhone,userEmail) values(?,?,?,?);",new Object[]{
                user.getUserID(),user.getUserName(),user.getUserPhone(),user.getUserEmail()
        });
        user_db.close();
    }
    public int getUserID()
    {
        SQLiteDatabase user_db = helper.getReadableDatabase();
        Cursor cursor = user_db.query("user_info", null,null,null,null,null,null);
        cursor.moveToLast();
        int userID =cursor.getInt(cursor.getColumnIndex("userID"));
        return userID;
    }
    public boolean islogin()
    {
        SQLiteDatabase user_db = helper.getReadableDatabase();
        Cursor cursor=user_db.query("user_info",null,null,null,null,null,null);
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
            return false;

    }
    public void logout()
    {
        SQLiteDatabase user_db = helper.getWritableDatabase();
        user_db.execSQL("delete  from  user_info");
    }
    public String getUserName(){
        SQLiteDatabase user_db = helper.getReadableDatabase();
        Cursor cursor = user_db.query("user_info", null,null,null,null,null,null);
        cursor.moveToLast();
        String userName =cursor.getString(cursor.getColumnIndex("userName"));
        return userName;
    }
    //检查用户是否已经注册
    public boolean isRegister(String userEmail)
    {
        boolean register = true;
        SQLiteDatabase user_db = helper.getReadableDatabase();
        Cursor cursor = user_db.rawQuery("select userEmail from user_info where userEmail = ?;",new String[]{userEmail});

        if (!cursor.moveToLast()) {
            register =false;
        }
        cursor.close();
        user_db.close();
        return register ;
    }
}
