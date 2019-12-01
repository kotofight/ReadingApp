package com.example.myapplication.FileUtil;

import android.util.Log;

import com.example.myapplication.RequiestService.FileService;
import com.example.myapplication.RequiestService.ResponseRec;
import com.example.myapplication.RequiestService.RetrofitGet;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoadUpThread extends Thread {
    public LoadUpThread(){
    }
    public LoadUpThread(UploadLisener lisener,String filePath,int userId){
        this.lisener=lisener;
        this.filePath=filePath;
        id = userId;
    }
    private UploadLisener lisener;
    private String filePath;
    int id;
    @Override
    public void run() {
        upload();
    }

    //文件上传
    private void upload(){
        File file = new File(filePath);
        final Retrofit retrofit = RetrofitGet.getRetrofit();
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("userId",String.valueOf(id))
                .addFormDataPart("file",file.getName(),requestBody)
                .build();
        Call<ResponseRec> uploadCall  = retrofit.create(FileService.class).upload(body);
        uploadCall.enqueue(new Callback<ResponseRec>() {
            @Override
            public void onResponse(Call<ResponseRec> call, Response<ResponseRec> response) {
                ResponseRec responseRec = response.body();
                Log.i("文件上传:",responseRec.getMsg());
                lisener.isOk(responseRec.getMsg());
            }
            @Override
            public void onFailure(Call<ResponseRec> call, Throwable t) {
                lisener.isError(t.toString());
                Log.e("文件上传:","失败"+t.getMessage());
            }
        });
    }

    //不使用
    private boolean uploadFile(){
        try {
            String uploadUrl = "";
            URL url = new URL(uploadUrl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            // 允许Input、Output，不使用Cache
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);

            con.setConnectTimeout(50000);
            //con.setReadTimeout(50000);
            // 设置传送的method=POST
            con.setRequestMethod("POST");
            //在一次TCP连接中可以持续发送多份数据而不会断开连接
            con.setRequestProperty("Connection", "Keep-Alive");
            //设置编码
            con.setRequestProperty("Charset", "UTF-8");
            //设置上传文件的编码格式
            con.setRequestProperty("Content-Type","multipart/form-data");
            con.setRequestProperty("userId","001");
            //con.setRequestProperty("Content-Type", "application/octet-stream");
            File file = new File(filePath);
            //con.setRequestProperty("fileName", URLEncoder.encode(file.getName(),"utf-8"));
            // 设置文件输出流DataOutputStream
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());

            // 获得文件输入流的FileInputStream
            FileInputStream fStream = new FileInputStream(file);
            // 设置每次写入1024bytes
            int bufferSize = 2*1024;
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
            System.out.println("报错信息： " + e.toString());
        }
        return false;
    }
    public interface UploadLisener{
        void isOk(String msg);
        void isError(String msg);
    }
}
