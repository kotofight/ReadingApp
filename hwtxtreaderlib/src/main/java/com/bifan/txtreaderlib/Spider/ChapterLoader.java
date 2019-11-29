package com.bifan.txtreaderlib.Spider;

import android.util.Log;

import com.bifan.txtreaderlib.MyConfig;
import com.bifan.txtreaderlib.Spider.Lisener.DownloadLisener;

import java.util.ArrayList;
import java.util.List;

public class ChapterLoader {
    Introduction introduction;

    public static int chapterIndex=0; //章节标记
    public int getChapterIndex() {
        return chapterIndex;
    }
    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }
    public ChapterLoader(Introduction introduction) {
        this.introduction = introduction;
    }
    public ChapterLoader(){}

    //加载一章
    private Chapter loadOneChapter(String baseUrl,String uri){
        String name;
        String content;
        Webpage tmp = new Webpage(baseUrl+uri,"gbk");
        Log.i("小说",baseUrl+uri);
        if(tmp.getCapterContext()){
            content = tmp.getContent();
            name = tmp.getTitle();
            return new Chapter(name,content);
        }
        return null;
    }
    //加载后续num章
    public List<Chapter> nextChapters(int num){
        List<Chapter> list = new ArrayList<>();
        List<Chapter> tmp;
        if(null!=introduction&&null!=introduction.getCatalog()){
            tmp = introduction.getCatalog();

            for(int i=0;i<num;i++){
                if(chapterIndex<tmp.size()){
                    Chapter chapter1 = loadOneChapter(MyConfig.BookBaseUrl,tmp.get(chapterIndex++).getContent());
                    list.add(chapter1);
                }
            }
            return list;
        }
        list.add(new Chapter("网络访问错误","小说加载失败"));
        return list;
    }
    //加载所有章节
    public List<Chapter> loadAllChapters(DownloadLisener lisener){
        Log.i("Net小说下载",introduction.toString());
        List<Chapter> list = new ArrayList<>();
        List<Chapter> tmp;
        int pro=0;
        if(null!=introduction&&null!=introduction.getCatalog()){
            tmp = (introduction.getCatalog());
            lisener.downloadStart(tmp.size());//--监听下载开始
            for(Chapter chapter:tmp){
                Chapter chapter1 = loadOneChapter(MyConfig.BookBaseUrl,chapter.getContent());
                list.add(chapter1);
                lisener.downloadProgress(pro++,tmp.size());//--监听下载进度
            }
            lisener.downloadEnd(list);//--监听下载完成
            return list;
        }
        lisener.downloadError();//--监听下载错误
        return null;
    }
}
