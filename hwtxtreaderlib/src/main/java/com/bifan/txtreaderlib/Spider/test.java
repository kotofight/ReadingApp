package com.bifan.txtreaderlib.Spider;

import java.util.List;

public class test {
    final static String myUrl= "https://qxs.la";
    public static void main(String[] args) {
            //BookSearch.searchBaseBook("末");
        //testIntro();
        //testCapter();
        //testSearch("重生");

        /*String[] s = "href=\"/244403/\">兵王之王</a>".split("href=.*?</a>");
        for (String str : s) {
            System.out.println(str);

        }*/
        //testLook("stat");
    }


    public static void testLook(String book){
        List<Introduction> list = BookSearch.serachBook(book);
        Introduction introduction = list.get(1);
        introduction = new IntroductionBuilder().getIntroduction(myUrl,introduction);
        printIntro(introduction);
    }
    public static void testIntro(){
        Introduction introduction= new IntroductionBuilder().getIntroduction(myUrl,"/257679/");
        System.out.println("作者："+introduction.getAuthor());
        System.out.println("小说名："+introduction.getName());
        System.out.println("类型："+introduction.getStyle());
        System.out.println("最新章节："+introduction.getNewest());
        System.out.println("更新时间："+introduction.getLastTime());
        System.out.println("简介："+introduction.getIntroduction());
        for(Chapter chapter : introduction.getCatalog()){
            System.out.println(chapter.getName()+"链接："+chapter.getContent());
        }
    }
    public static void testSearch(String bookName){
        List<Introduction> list = BookSearch.serachBook(bookName);
        printIntro(list);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        list = BookSearch.nextPage();
        printIntro(list);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        list = BookSearch.nextPage();
        printIntro(list);
        list = BookSearch.prePage();
        printIntro(list);
        list = BookSearch.prePage();
        printIntro(list);
        list = BookSearch.prePage();
        printIntro(list);
    }
    public static void printIntro(Introduction introduction){
        System.out.println("小说名称:  "+introduction.getName());
        System.out.println("face链接:  "+introduction.getFaceUrl());
        System.out.println("小说作者:  "+introduction.getAuthor());
        System.out.println("最近更新章节:  "+introduction.getNewest());
        System.out.println("最近更新时间:  "+introduction.getLastTime());
        for(Chapter c : introduction.getCatalog()){
            System.out.println(c.getName());
            System.out.println(c.getContent());
        }
    }
    public static void printIntro(List<Introduction> list){
        if(list==null)
            return;
        for(Introduction introduction : list){
            System.out.println("小说名称:  "+introduction.getName());
            System.out.println("face链接:  "+introduction.getFaceUrl());
            System.out.println("小说作者:  "+introduction.getAuthor());
            System.out.println("最近更新章节:  "+introduction.getNewest());
            System.out.println("最近更新时间:  "+introduction.getLastTime());
        }
    }
   public static void testCapter(){
        Webpage webpage = new Webpage(myUrl+"/177913/19168708/","gbk");
        if(webpage.getCapterContext()){
            System.out.println(webpage.getTitle());
            System.out.println(webpage.getNextCapterUrl());
            System.out.println(webpage.getContent());
        }

    }
}
