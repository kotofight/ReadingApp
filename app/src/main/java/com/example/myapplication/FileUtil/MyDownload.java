package com.example.myapplication.FileUtil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import com.bifan.txtreaderlib.Spider.BookBean;
import com.example.myapplication.Common.Bean.Book;
import com.example.myapplication.Listener.DownloadListener;
import com.example.myapplication.R;
import com.example.myapplication.RequiestService.DownloadService;
import com.example.myapplication.RequiestService.RetrofitGet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.core.app.NotificationCompat;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyDownload {
    private BookBean bookBean;
    Context context;
    //通知栏进度条
    private NotificationManager mNotificationManager=null;
    private Notification mNotification;
    int index=0;
    public MyDownload(Context context,BookBean bookBean) {
        this.context = context;
        this.bookBean = bookBean;
    }

    public BookBean getBookBean() {
        return bookBean;
    }

    public void setBookBean(BookBean bookBean) {
        this.bookBean = bookBean;
    }
    //下载
    public void download(final String appPath, final DownloadListener listener, final int index){
        this.index=index;
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
                    listener.startDownload(index,size);
                    Log.i("本地下载路径：",file.getAbsolutePath()+" 文件大小："+size);
                    notificationInit();//--开启下载通知
                    // 设置每次写入2*1024bytes
                    int bufferSize = 2*1024;
                    int posion = 0;
                    byte[] buffer = new byte[bufferSize];
                    int length = 0;
                    int downProgress = 0;
                    // 从文件读取数据至缓冲区
                    while ((length = dataInputStream.read(buffer)) != -1) {
                        // 将资料写入文件中
                        fileOutputStream.write(buffer, 0, length);
                        fileOutputStream.flush();
                        posion+=length;
                        //监听下载进度
                        int progress = posion*100/size;
                        if(progress==10){
                            downProgress+=progress;
                            setDownloadPro(downProgress);
                            listener.setDownloadPro(index,downProgress);
                            posion = 0;
                        }
                    }
                    //监听下载完成
                    bookName = file.getName();
                    endDownload(bookName);
                    listener.endDownload(index,new Book(bookName.substring(0,bookName.indexOf(".")),file.getAbsolutePath()));
                }catch (Exception e){
                    e.printStackTrace();
                    listener.errorDownload(index);//监听下载失败
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
                listener.errorDownload(index);
                errorDownload();
            }
        });
    }
    private void notificationInit(){
        //通知栏内显示下载进度条
        //PendingIntent pIntent=PendingIntent.getActivity(this, 0, intent, 0);
        mNotificationManager=(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(""+index,
                    "默认通知", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        mNotification=new NotificationCompat.Builder(context,""+index)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle("小说下载通知")
                .setWhen(System.currentTimeMillis())
                .setCustomContentView(new RemoteViews(context.getPackageName(),R.layout.download_notification_layout))
                .build();
        mNotificationManager.notify(index,mNotification);
    }
    //设置下载进度
    private void setDownloadPro(int downProgress) throws InterruptedException {
            Log.i("下载通知进度信息",""+downProgress);
           mNotification.contentView.setTextViewText(R.id.Notifi_title, "文件下载");
          mNotification.contentView.setProgressBar(R.id.notification_ProgressBar, 100, downProgress, false);
          mNotificationManager.notify(index, mNotification);
          Thread.sleep(500);
    }
    //下载完毕
    private void endDownload(String bookName) {
        Log.i("下载通知进度完成信息","id:"+index);
         mNotification.contentView.setProgressBar(R.id.notification_ProgressBar, 100, 100, false);
         mNotification.contentView.setTextViewText(R.id.Notifi_title, bookName+" 下载完毕");
         mNotificationManager.notify(index, mNotification);
    }
    //下载错误
    private void errorDownload(){
        Log.i("下载信息","下载失败");
        mNotification.contentView.setTextViewText(R.id.Notifi_title, "文件下载失败!");
        mNotificationManager.notify(index, mNotification);
    }





    @Override
    public String toString() {
        return "MyDownload{" +
                "bookBean=" + bookBean +
                '}';
    }
}
