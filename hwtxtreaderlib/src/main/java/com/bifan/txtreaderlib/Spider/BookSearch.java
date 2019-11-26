package com.bifan.txtreaderlib.Spider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//搜索小说
public class BookSearch {
    private static String book;
    private static int page;
    private static String mainUrl = "https://qxs.la/";

    public static String getMainUrl() {
        return mainUrl;
    }

    public static void setMainUrl(String mainUrl) {
        BookSearch.mainUrl = mainUrl;
    }

    /**
     *
     * @param bookName 小说名称
     * @return 返回符合小说名称的 List<Introduction></>
     */
    public static List<Introduction> serachBook(String bookName){
        page = 1;
        return search(bookName);
    }

    private static List<Introduction> search(String bookName){
        book = bookName;
        ArrayList<Introduction> list = new ArrayList<>();
        String html = DownloadUtil.getHtml(mainUrl+"s_"+bookName+"/"+page+"/");
        //System.out.println("page:"+page+" 网页源码："+html);

        Pattern pattern0;
        Matcher matcher0;
        pattern0 = Pattern.compile("<ul class=\"list_content\">.*?</ul>");
        matcher0 = pattern0.matcher(html);
        while (matcher0.find()){
            //System.out.println("一个小说");
            Pattern pattern;
            Matcher matcher;
            String temp;
            String[] li;
            Introduction introduction = new Introduction();
            String ul = matcher0.group();
            //获取小说名
            pattern = Pattern.compile("href=.*?</a>");
            matcher = pattern.matcher(ul);
            if(matcher.find()){
                temp = matcher.group();
                li = temp.split("\"|<|>");
                introduction.setName(li[3]);
                //System.out.println("获得小说名"+li[2]);
                introduction.setFaceUrl(li[1]);
            }
            //获取最新章节
            if(matcher.find()){
                li = matcher.group().split("\"|<|>");
                //System.out.println("获得最新章节"+li[2]);
                introduction.setNewest(li[2]);
            }

            //获取作者名称
            if(matcher.find()){
                li = matcher.group().split("\"|<|>");
               // System.out.println("获得作者名称"+li[2]);
                introduction.setAuthor(li[3]);
            }

            //获取最新更新时间
            pattern = Pattern.compile("cc5.*?</li>");
            matcher = pattern.matcher(ul);
            if(matcher.find()){
                li = matcher.group().split("\"|<|>");
                //System.out.println("更新时间"+li[2]);
                introduction.setLastTime(li[2]);
            }
            if(introduction.getName()!=null)
                list.add(introduction);
        }
        return list;
    }

    /**
     *
     * @return 下一页内容
     */
    public static List<Introduction> nextPage(){
        if(book!=null) {
            page+=1;
            return search(book);
        }
        else return null;
    }

    /**
     *
     * @return 上一页内容
     */
    public static List<Introduction> prePage(){
        if(book!=null&&page>1) {
            page-=1;
            return search(book);
        }
        else return null;
    }


}
