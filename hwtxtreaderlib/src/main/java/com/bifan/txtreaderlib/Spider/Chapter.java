package com.bifan.txtreaderlib.Spider;

public class Chapter {
    private String name;
    private String Content;

    public Chapter(String name, String content) {
        this.name = name;
        Content = content;
    }
    public Chapter(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}
