package com.example.myapplication.FileUtil;

import android.util.Log;

import com.bifan.txtreaderlib.Spider.BookBean;
import com.example.myapplication.Common.Bean.Book;
import com.example.myapplication.Listener.DownloadListener;
import com.example.myapplication.RequiestTest.DownloadService;
import com.example.myapplication.RequiestTest.RetrofitGet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyDownload {
    private BookBean bookBean;

    public MyDownload(BookBean bookBean) {
        this.bookBean = bookBean;
    }

    public BookBean getBookBean() {
        return bookBean;
    }

    public void setBookBean(BookBean bookBean) {
        this.bookBean = bookBean;
    }
    //下载
    public void download(final String appPath,final DownloadListener listener){
        Retrofit retrofit = RetrofitGet.getRetrofit();
        Log.i("开始下载","本地下载路径："+appPath);
        retrofit.create(DownloadService.class).download(bookBean).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String baseDir = appPath+"/mybooks";
                File file = new File(baseDir);
                if(!file.exists()){
                    try {
                        file.mkdirs();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //获取文件名，若与本地文件重复，修改文件名
                String bookName = bookBean.getFileName();
                file = new File(file,bookName);
                for(int i = 1;file.exists();i++){
                    file = new File(baseDir,bookName.substring(0,bookName.indexOf("."))+"("+i+")"+bookName.substring(bookName.indexOf(".")));
                }
                if(!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //数据输入流
                DataInputStream dataInputStream = new DataInputStream(response.body().byteStream());
                // 获得文件输出流
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    //文件大小
                    int size =dataInputStream.available();
                    //监听开始下载
                    listener.startDownload(size);
                    Log.i("本地下载路径：",file.getAbsolutePath()+" 文件大小："+size);

                    // 设置每次写入2*1024bytes
                    int bufferSize = 2*1024;
                    int posion = 0;
                    byte[] buffer = new byte[bufferSize];
                    int length = 0;
                    // 从文件读取数据至缓冲区
                    while ((length = dataInputStream.read(buffer)) != -1) {
                        // 将资料写入文件中
                        fileOutputStream.write(buffer, 0, length);
                        fileOutputStream.flush();
                        posion+=length;
                        //监听下载进度
                        int progress = posion*100/size;
                        if(progress==10){
                            listener.setDownloadPro(progress);
                            posion = 0;
                        }
                    }
                    //监听下载完成
                    bookName = file.getName();
                    listener.endDownload(new Book(bookName.substring(0,bookName.indexOf(".")),file.getAbsolutePath()));

                }catch (Exception e){
                    e.printStackTrace();
                    listener.errorDownload();//监听下载失败
                }finally {
                    try {
                        if(fileOutputStream!=null)
                            fileOutputStream.close();
                        if(dataInputStream!=null)
                            dataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.errorDownload();
            }
        });
    }
    @Override
    public String toString() {
        return "MyDownload{" +
                "bookBean=" + bookBean +
                '}';
    }
}
