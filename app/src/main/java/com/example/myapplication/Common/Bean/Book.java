package com.example.myapplication.Common.Bean;

import com.example.myapplication.R;

import org.litepal.crud.LitePalSupport;

//书架
public class Book extends LitePalSupport {
    private  String bookName;
    private  int bookImage;
    private String path;
    private String position;
    public Book(){
        bookImage = R.drawable.book2;
    }
    public Book(String bookName, int bookImage)
    {
        this();
        this.bookName=bookName;
        this.bookImage=bookImage;
    }

    public Book(String bookName, String path) {
        this();
        this.bookName = bookName;
        this.path = path;
    }

    public Book(String bookName, int bookImage, String path, String position) {
        this();
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.path = path;
        this.position = position;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookImage() {
        return bookImage;
    }

    public void setBookImage(int bookImage) {
        this.bookImage = bookImage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
