package com.example.myapplication.Common;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.bifan.txtreaderlib.Spider.BookBean;
import com.bifan.txtreaderlib.Spider.BookSearch;
import com.bifan.txtreaderlib.Spider.Introduction;
import com.example.myapplication.Common.Adapter.PreviewAdapter;
import com.example.myapplication.Common.Adapter.SoureAdapter;
import com.example.myapplication.Common.Bean.Book;
import com.example.myapplication.FileUtil.MyDownload;
import com.example.myapplication.FileUtil.SearchUtil;
import com.example.myapplication.Listener.DownloadListener;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Result2Activity extends AppCompatActivity implements DownloadListener{
    private List<Introduction> previewList=new ArrayList<>();
    private final List<BookBean> soureList=new ArrayList<>();
    private String bookName;
    //private List<Book> bookList=new ArrayList<>();
    ListView listView1;//网站资源
    ListView listView2;//服务器资源
    public static final int DOWN_START = 0;
    public static final int DOWN_PROGRESS = DOWN_START + 1;
    public static final int DOWN_SUCCESS = DOWN_PROGRESS + 1;
    public static final int DOWN_FAILED = DOWN_SUCCESS + 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result2b);
        //获得搜索的文件名
        bookName = getIntent().getBundleExtra("bookName").get("bookName").toString();

        listView1 =(ListView) findViewById(R.id.previewlistid);
        listView2 =(ListView) findViewById(R.id.soureListid);

        new MyTask().execute();//--网站资源+服务器资源
        //initSoure();//--

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Introduction book=previewList.get(position);
                //Toast.makeText(ResultActivity.this,book.getName(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Result2Activity.this,IntroductionActivity.class);
                intent.putExtra("faceUrl",book.getFaceUrl());
                startActivity(intent);
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(soureList.get(position).getFileNum()==-1){
                    return;
                }
                    download(position);
            }
        });
       // EditText mypreview=(EditText) findViewById(R.id.mypreview);
       ImageButton back_button=(ImageButton)findViewById(R.id.backbutton);
       //文本框上移代码
        final View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
 decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
        //返回
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //文本框上移代码
    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;
                if (diff != 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        contentView.setPadding(0, 0, 0, diff);
                    }
                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(0, 0, 0, 0);
                    }
                }
            }
        };
    }

    //回调接口实现--服务器资源
    @Override
    public void startDownload(int size) {
        Log.i("下载信息","开始下载,文件大小："+size);
    }
    int downProgress=0;
    @Override
    public void setDownloadPro(int position) {
        downProgress+=position;
                    //Toast.makeText(this,pro,Toast.LENGTH_SHORT);
        Log.i("下载信息","文件下载进度："+downProgress+"%");
        Toast.makeText(getApplicationContext(),"文件下载进度："+downProgress+"%",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void endDownload(Book book) {
        Log.i("下载信息","下载完毕");
        Toast.makeText(getApplicationContext(),book.getBookName()+" 下载完毕",Toast.LENGTH_SHORT).show();
        addToDatabase(book);
    }
    @Override
    public void errorDownload(){
        Log.i("下载信息","下载失败");
        Toast.makeText(getApplicationContext(),"下载失败!",Toast.LENGTH_SHORT).show();
    }


    //线程类，用于在子线程中更新主线程中的View资源
    class MyTask extends AsyncTask<String ,Integer,String> {
        @Override
        protected void onPostExecute(String s) {
            if(soureList.size()<1||soureList==null){
                soureList.add(new BookBean("暂无该资源分享",-1));
            }
            SoureAdapter adapter1 =new SoureAdapter(Result2Activity.this,R.layout.source_list,soureList);
            listView2.setAdapter(adapter1);
            PreviewAdapter adapter =new PreviewAdapter(Result2Activity.this,R.layout.book3_list,previewList);
            listView1.setAdapter(adapter);
        }
        @Override
        protected String doInBackground(String... strings) {
            initSoure();
            initPreview();
            return null;
        }
    }

    //爬虫获取网上资源
    private void initPreview(){
        previewList = BookSearch.serachBook(bookName);
    }
    //从服务器获取资源
    private void initSoure(){
        soureList.clear();
        SearchUtil.searchBaseBook(bookName,soureList);
    }

    //下载确认提示----服务器资源
    private void download(int position){
        final BookBean bookBean = soureList.get(position);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("下载确定",bookBean.toString());
                Toast.makeText(getApplicationContext(),"开始下载",Toast.LENGTH_LONG).show();
                loadStart(bookBean);
            }
        });
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setTitle("下载提示");
        alert.setMessage("是否开始下载？");
        alert.create().show();
        //alert.show();
    }

    //调用下载线程开始下载--服务器资源
    void loadStart(BookBean bookBean){
        new MyDownload(bookBean).download(Environment.getExternalStorageDirectory().getAbsolutePath(),this);
    }

    //将下载好的文件添加到书架（数据库）
    private void addToDatabase(Book book){
        book.save();
    }
}
