package com.example.myapplication.Common.Bean;

public class SBook {
    private String name;
    private int imageId;

    public SBook(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }
    public String getName(){
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
