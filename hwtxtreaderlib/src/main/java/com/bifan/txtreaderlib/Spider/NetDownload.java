package com.bifan.txtreaderlib.Spider;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import com.bifan.txtreaderlib.MyConfig;
import com.bifan.txtreaderlib.R;
import com.bifan.txtreaderlib.Spider.Lisener.DataLisener;
import com.bifan.txtreaderlib.Spider.Lisener.DownloadLisener;

import java.io.File;
import java.util.List;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NetDownload {
    //通知栏进度条
    private NotificationManager mNotificationManager=null;
    private Notification mNotification;
    int index=0;
    Context context;
    DataLisener lisener;
    final Result result = new Result();
    public NetDownload(DataLisener dataLisener) {
        lisener = dataLisener;
    }

    public void download(final String ContextPath, final Introduction introduction, final Context context, final int index){
        this.context = context;
        this.index = index;
        ChapterLoader loader = new ChapterLoader(introduction);
        loader.loadAllChapters(new DownloadLisener() {
            @Override
            public void downloadStart(int size) {
                notificationInit();
                Log.i("小说下载开始信息","下载开始,章节数："+size);
            }

            @Override
            public void downloadProgress(int progress,int size) {
                try {
                    if(size>0&&progress%(size/10)==0){
                        setDownloadPro(progress,size);
                        Log.i("小说下载进度信息：",progress+"");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void downloadEnd(List<Chapter> chapters) {
                endDownload(introduction,chapters);
                lisener.addToDatabase(result.getResult());
                Log.i("小说下载信息：","小说下载完毕");
            }

            @Override
            public void downloadError() {
                Log.i("小说下载信息：","小说下载错误");
                result.setResult(new Result(false,"下载失败",""));
                errorDownload();
            }
        });
    }
    private void notificationInit(){
        //通知栏内显示下载进度条
        mNotificationManager=(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(""+index,
                    "默认通知", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        mNotification=new NotificationCompat.Builder(context,""+index)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.pic2)
                .setAutoCancel(true)
                .setContentTitle("小说下载通知")
                .setWhen(System.currentTimeMillis())
                .setCustomContentView(new RemoteViews(context.getPackageName(),R.layout.download_notification_layout))
                .build();
        mNotificationManager.notify(index,mNotification);
    }
    //设置下载进度
    private void setDownloadPro(int downProgress,int size) throws InterruptedException {
        Log.i("下载通知进度信息",""+downProgress);
        mNotification.contentView.setTextViewText(R.id.Notifi_title, "文件下载");
        mNotification.contentView.setProgressBar(R.id.notification_ProgressBar, size, downProgress, false);
        mNotificationManager.notify(index, mNotification);
        //Thread.sleep(500);
    }
    //下载完毕
    private void endDownload(Introduction introduction,List<Chapter> chapters) {
        final String baseDir = MyConfig.baseDir;
        File file = FileUtil.writeToFile(baseDir,introduction.getName()+".txt",chapters);
        String fileName = file.getName();
        result.setResult(new Result(true,fileName.substring(0,fileName.indexOf(".")),file.getAbsolutePath()));
        Log.i("下载通知进度完成信息","id:"+index);
        mNotification.contentView.setProgressBar(R.id.notification_ProgressBar, 100, 100, false);
        mNotification.contentView.setTextViewText(R.id.Notifi_title, result.getResult().getName()+" 下载完毕");
        mNotificationManager.notify(index, mNotification);
    }
    //下载错误
    private void errorDownload(){
        Log.i("下载信息","下载失败");
        mNotification.contentView.setTextViewText(R.id.Notifi_title, "文件下载失败!");
        mNotificationManager.notify(index, mNotification);
    }
}
