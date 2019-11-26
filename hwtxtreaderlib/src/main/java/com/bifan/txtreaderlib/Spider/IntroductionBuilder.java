package com.bifan.txtreaderlib.Spider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntroductionBuilder {

    /**
     *
     * @param parUrl 网站主页
     * @param introduction 小说简介（faceUrl!=null）
     * @return 小说简介(包含章节目录list)
     */
    public Introduction getIntroduction(String parUrl,Introduction introduction){
        if(null==introduction||null==introduction.getFaceUrl()||introduction.getFaceUrl().equals(""))
            return null;
        return getIntroduction(parUrl,introduction.getFaceUrl());
    }

    /**
     *
     * @param parUrl 网站主页
     * @param childUrl 小说简介页uri
     * @return 小说内容（名称、作者、章节目录）
     */
    public Introduction getIntroduction(String parUrl,String childUrl){
        Introduction introduction = new Introduction();
        List<Chapter> list  = new ArrayList<>();
        String html;//网站源码
        String[] strings;
        Pattern pattern;
        Matcher matcher;
        String temp;
        //获得html源码
        html = DownloadUtil.getHtml(parUrl+childUrl);
        //System.out.println(html);
        //获取小说名称
        pattern = Pattern.compile("<h1>.*?</a>");
        matcher = pattern.matcher(html);
        if(matcher.find()){
            temp = matcher.group(0);
            strings = temp.split("\"|<|>");
            introduction.setName(strings[strings.length-2]);
            introduction.setFaceUrl(strings[strings.length-4]);
        }
        //获取作者名
        pattern = Pattern.compile("作 者：.*?</a>");
        matcher = pattern.matcher(html);
        if(matcher.find()){
            temp = matcher.group();
            strings = temp.split("\"|<|>");
            introduction.setAuthor(strings[strings.length-2]);
        }
        //获取类型
        pattern = Pattern.compile("类型：.*?</font>");
        matcher = pattern.matcher(html);
        if(matcher.find()){
            temp = matcher.group();
            strings = temp.split("\"|<|>");
            introduction.setStyle(strings[strings.length-2]);
        }
        //最新章节和更新时间：
        pattern = Pattern.compile("最新章节：.*?</div>");
        matcher = pattern.matcher(html);
        if(matcher.find()){
            temp = matcher.group();
            strings = temp.split("\"|<|>");
            introduction.setLastTime(strings[strings.length-2]);
            introduction.setNewest(strings[strings.length-4]);
        }
        //获取小说简介

        pattern = Pattern.compile("<div class=\"desc\">.*?</div>");
        matcher = pattern.matcher(html);
        if(matcher.find()){
            temp = matcher.group();
            //替换回车和空格
            pattern = Pattern.compile("<br />|<br>|<br/>");
            matcher = pattern.matcher(temp);
            if(matcher.find()){
                temp = matcher.replaceAll("\n");
            }
            temp = temp.replaceAll("&nbsp;"," ");
            temp = temp.substring(18,temp.length()-6);
            introduction.setIntroduction(temp);

        }

        //获取章节列表
        pattern = Pattern.compile("<div class=\"chapter\">.*?</div>");
        matcher = pattern.matcher(html);
        String addr;
        String content;
        while (matcher.find()){
            temp = matcher.group();
            if(temp.length()<10)
                continue;
            temp = temp.substring(temp.indexOf("href")+6,temp.indexOf("</a>"));
            addr = temp.substring(0,temp.indexOf("title")-2);

            content = temp.substring(temp.indexOf("title=")+7,temp.indexOf(">")-1);
            //System.out.println(addr + "内容："+content);
            Chapter chapter = new Chapter(content,addr);
            list.add(chapter);
        }
        introduction.setCatalog(list);
        return introduction;
    }

}
