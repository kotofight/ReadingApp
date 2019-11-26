package com.example.myapplication.Common.Bean;

public class Preview {
   // private String previewText;
    private String bookThur;
    //private int imageId;
    private String bookName;
    public Preview(String bookThur,String bookName){
        //this.previewText=previewText;
        this.bookName=bookName;
        this.bookThur=bookThur;
        //this.imageId=imageId;
    }
    /*public String getPreviewText()
    {
        return previewText;
    }*/
    public String getBookThur()
    {
        return bookThur;
    }
    public String getBookName()
    {
        return bookName;
    }
}
