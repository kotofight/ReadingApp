package com.bifan.txtreaderlib.Spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUtil {
    /**
     *
     * @param pageUrl 网页url
     * @return Http连接
     */
    public static HttpURLConnection getConnection(String pageUrl){
        HttpURLConnection con = null;
        try {
            //构建一URL对象
            URL url = new URL(pageUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Connection","keep-alive");
            con.setRequestProperty("Cache-Control","max-age=0");
            con.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            //con.setRequestProperty("Accept-Encoding","gzip, deflate, br");
            con.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");//zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
            con.setRequestProperty("Upgrade-Insecure-Requests","1");
            con.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");//Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");//"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/18.17763");//
            con.connect();
            return con;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param startUrl 网站url
     * @return 网页html源码
     */
    public static String getHtml(String startUrl){
        StringBuffer sb = new StringBuffer();
        HttpURLConnection con = null;
        try {
             con = DownloadUtil.getConnection(startUrl);
            if(con.getResponseCode() == 200){
                //使用openStream得到一输入流并由此构造一个BufferedReader对象
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "gbk"));
                String line;
                //读取www资源
                while ((line = in.readLine()) != null)
                {
                    //System.out.println(line);
                    sb.append(line);
                }
                in.close();
            }else {

                sb.append("网站连接错误");
                System.out.println("网站连接错误"+con.getResponseCode()+con.getResponseMessage());
            }
        }
        catch (Exception ex)
        {
            System.err.println(ex + " 异常！！");
        }finally {
            if(con!=null)
                con.disconnect();
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }


}
