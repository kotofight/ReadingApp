/**
 * 网页操作相关类
 */
package com.bifan.txtreaderlib.Spider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 侯思其
 * 可通过该类获得小说章节内容和标题
 */
public class Webpage {
    private String pageUrl;//定义需要操作的网页地址
    private String pageEncode="gbk";//定义需要操作的网页的编码
    private String title;//章节标题
    private String content;//内容
    private String nextCapterUrl;//下一章节的url
    private boolean isErrer;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageEncode() {
        return pageEncode;
    }

    public void setPageEncode(String pageEncode) {
        this.pageEncode = pageEncode;
    }

    public String getNextCapterUrl() {
        return nextCapterUrl;
    }

    public void setNextCapterUrl(String nextCapterUrl) {
        this.nextCapterUrl = nextCapterUrl;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Webpage(String pageUrl, String pageEncode) {
        this.pageUrl = pageUrl;
        this.pageEncode = pageEncode;
    }

    public Webpage() {
    }
    final  String regEx_Title = "<h1>.*</h1>";//定义标题名称表达式
    final  String regEx_BR = "<br />|<br>|<br/>";//定义<br />的替换
    final  String regEx_Other = "<.*>|<.*</div>";//去除多余标签
    final  String regEx_MainContent = "<br/>.*<br/>";//定义正文标签的正则表达式
    final String regEx_nextCapterUrl = "id=\"nextLink\".*?>";//下一章节的链接


    final String regEx_content = "<div id=\"content\" name=\"content\">.*</div>";
    final String regEx_end = "<div class=\"bottem\">.*$";
    final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    //定义取源码的方法
    public String getPageSource()
    {
        StringBuffer sb = new StringBuffer();
        HttpURLConnection con = null;
        try {
             con = DownloadUtil.getConnection(pageUrl);
            if(con.getResponseCode() == 200){
                //使用openStream得到一输入流并由此构造一个BufferedReader对象
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), pageEncode));
                String line;
                //读取www资源
                while ((line = in.readLine()) != null)
                {
                    sb.append(line);
                }
                in.close();
            }else {
                System.out.println("网站连接错误！" + " code:"+con.getResponseCode()+" errer: "+con.getResponseMessage());
               /* URL url = new URL("https://qxs.la/177913/19168708/");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                System.out.println("code: "+ connection.getResponseCode());*/
            }
            isErrer = false;

        }
        catch (Exception ex)
        {
            isErrer = true;
            System.err.println(ex + "异常！！");
        }finally {
            if(con!=null){
                con.disconnect();
            }
        }
        return sb.toString();
    }

    /**
     *
     * @return 是否获得章节内容
     */
    public boolean getCapterContext(){
        if(isErrer||pageUrl==null) {
            return false;
        }
        String htmlStr = getPageSource();//获取未处理过的源码
        System.out.println(htmlStr);
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(regEx_Title);//获取标题
        matcher = pattern.matcher(htmlStr);
        if(matcher.find()){
            title = matcher.group();
            title = title.substring(4,title.length()-5);
        }

        pattern = Pattern.compile(regEx_nextCapterUrl);//获取下一章节的链接
        matcher = pattern.matcher(htmlStr);
        if(matcher.find()){
            nextCapterUrl = matcher.group();
            nextCapterUrl = nextCapterUrl.substring(20,nextCapterUrl.length()-2);
        }else nextCapterUrl = null;

        pattern = Pattern.compile(regEx_MainContent);//获取主体内容
        matcher = pattern.matcher(htmlStr);
        if(matcher.find()) {
            content = matcher.group();
        }
        if (null==content||content.isEmpty()){
            return false;
        }
        pattern = Pattern.compile(regEx_BR);//替换回车符
        matcher = pattern.matcher(content);
        if(matcher.find()){
            content = matcher.replaceAll("\n");
        }

        pattern = Pattern.compile(regEx_Other);//去除多余标签
        matcher = pattern.matcher(content);
        content = matcher.replaceAll("");
        if(content.equals("")||title.equals(""))
            return false;
        return true;
    }

    //定义一个把HTML标签删除过的源码的方法
    private boolean getCapterContext2()
    {
        if(isErrer||pageUrl==null)
            return false;

        //final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符



        String htmlStr = getPageSource();//获取未处理过的源码
        Pattern pattern;
        Matcher matcher;
        //获取章节标题
        pattern = Pattern.compile(regEx_Title,Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(htmlStr);
        if(matcher.find()){
            title = matcher.group();
            title = title.substring(4,title.length()-5);
        }
        else title = "未知章节名";


        //获取下一章节的链接
        pattern = Pattern.compile(regEx_nextCapterUrl,Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(htmlStr);
        if(matcher.find()){
            nextCapterUrl = matcher.group();
            nextCapterUrl = nextCapterUrl.substring(18,nextCapterUrl.length()-5);
        }else nextCapterUrl = null;

        //获取正文
        pattern = Pattern.compile(regEx_content,Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(htmlStr);
        if(matcher.find()){
            content = matcher.group();

        }
        else content = htmlStr;

        pattern = Pattern.compile(regEx_end,Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(content);
        content = matcher.replaceAll("");//过滤正文后文字

        pattern = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(content);
        content = matcher.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(content);
        content = m_style.replaceAll(""); // 过滤style标签

        Pattern p_br = Pattern.compile(regEx_BR, Pattern.CASE_INSENSITIVE);
        Matcher m_br = p_br.matcher(content);
        content = m_br.replaceAll("\n"); // 过滤回车标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(content);
        content = m_html.replaceAll(""); // 过滤html标签

        content = content.replaceAll("&nbsp;", " ");
        //System.out.println("结束");
        if(content.equals(""))
            return false;
        return true;
    }
}
