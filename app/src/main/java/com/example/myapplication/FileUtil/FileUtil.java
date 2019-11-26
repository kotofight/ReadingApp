package com.example.myapplication.FileUtil;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtil {
    /**
     * @param uploadUrl 上传路径
     * @param filePath 文件本地路径
     * @return
     */
    public static boolean uploadFile(String uploadUrl,String filePath){
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            // 允许Input、Output，不使用Cache
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);

            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            // 设置传送的method=POST
            con.setRequestMethod("POST");
            //在一次TCP连接中可以持续发送多份数据而不会断开连接
            con.setRequestProperty("Connection", "Keep-Alive");
            //设置编码
            con.setRequestProperty("Charset", "UTF-8");
            //设置上传文件的编码格式
            con.setRequestProperty("Content-Type", "application/octet-stream");

            // 设置文件输出流DataOutputStream
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());

            // 获得文件输入流的FileInputStream
            FileInputStream fStream = new FileInputStream(filePath);
            // 设置每次写入1024bytes
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int length = -1;
            // 从文件读取数据至缓冲区
            while ((length = fStream.read(buffer)) != -1) {
                // 将资料写入DataOutputStream中
                ds.write(buffer, 0, length);
            }
            ds.flush();
            fStream.close();
            ds.close();
            if(con.getResponseCode() == 200){
                System.out.println("文件上传成功！ 文件路径："+filePath);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传失败！上传文件为：" + filePath);
            System.out.println("报错信息toString：" + e.toString());
        }
        return false;
    }
}
