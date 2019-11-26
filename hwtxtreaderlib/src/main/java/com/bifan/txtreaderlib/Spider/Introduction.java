package com.bifan.txtreaderlib.Spider;

import java.util.List;

public class Introduction {
    private String name;//小说名称
    private String author;//小说作者
    private String style;//小说类型
    private String newest;//最新章节
    private String lastTime;//最近更新时间
    private String introduction;//简介
    private String faceUrl;//主页链接
    private List<Chapter> catalog;//章节列表

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getNewest() {
        return newest;
    }

    public void setNewest(String newest) {
        this.newest = newest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<Chapter> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<Chapter> catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return "Introduction{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", style='" + style + '\'' +
                ", newest='" + newest + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", introduction='" + introduction + '\'' +
                ", faceUrl='" + faceUrl + '\'' +
                ", catalog=" + catalog +
                '}';
    }
}
