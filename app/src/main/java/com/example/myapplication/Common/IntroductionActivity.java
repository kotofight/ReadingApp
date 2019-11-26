package com.example.myapplication.Common;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bifan.txtreaderlib.OneIntroduction;
import com.bifan.txtreaderlib.Spider.BookSearch;
import com.bifan.txtreaderlib.Spider.Chapter;
import com.bifan.txtreaderlib.Spider.ChapterLoader;
import com.bifan.txtreaderlib.Spider.Introduction;
import com.bifan.txtreaderlib.Spider.IntroductionBuilder;
import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.example.myapplication.MyConfig;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IntroductionActivity extends AppCompatActivity {
    TextView nameView;
    TextView authorView;
    TextView neweastCapterView;
    TextView introView;
    Button startButton;
    String faceUrl;
    Introduction introduction;
    boolean loaded = false;
    List<Chapter> list;
    String bookPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce1);
        startButton = (Button)findViewById(R.id.start_read);
        nameView = (TextView)findViewById(R.id.intro_book_name);
        authorView = (TextView)findViewById(R.id.intro_author);
        neweastCapterView = (TextView)findViewById(R.id.Intro_newestCapter);
        introView = (TextView)findViewById(R.id.intro_intro);
        faceUrl = getIntent().getStringExtra("faceUrl");
        MyTask myTask = new MyTask();
        myTask.execute();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){
                    //进入小说阅读界面
                    //设置横屏显示
                    TxtConfig.saveIsOnVerticalPageMode(getApplicationContext(),false);
                    //设置默认字体大小
                    TxtConfig.saveTextSize(getApplicationContext(),42);
                    /*Intent intent = new Intent();
                    intent.putExtra("FilePath", bookPath);
                    intent.putExtra("FileName", introduction.getName());
                    intent.setClass(getApplicationContext(), HwTxtPlayActivity.class);
                    startActivity(intent);*/
                    //HwTxtPlayActivity.loadTxtFile(getApplicationContext(),bookPath);
                    Intent intent = new Intent();
                    intent.putExtra("ContentStr","小说内容");
                    intent.putExtra("FileName", introduction.getName());
                    intent.setClass(getApplicationContext(), HwTxtPlayActivity.class);
                    OneIntroduction.introduction = introduction;
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"网络加载中，请等待...",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    class MyTask extends AsyncTask<String ,Integer, List<Introduction>> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(List<Introduction> introductions) {

            nameView.setText(introduction.getName());
            authorView.setText(introduction.getAuthor());
            neweastCapterView.setText(introduction.getNewest());
            introView.setText(introduction.getIntroduction());
            introView.requestLayout();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected List<Introduction> doInBackground(String... strings) {
            introduction = new IntroductionBuilder().getIntroduction(BookSearch.getMainUrl(),faceUrl);
            //loadOneCapterToFile();
            return null;
        }

    }
    ChapterLoader loader;
    //默认先加载一章章节内容
    private void loadOneCapterToFile() {
        loader = new ChapterLoader(introduction);
        loadNextCaptersToFile(1);
    }
    private void loadNextCaptersToFile(final int num){
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = new ArrayList<>();
                list.addAll(loader.nextChapters(num));
                //list=introduction.getCatalog();
                //FileUtil.appendToFile(MyConfig.baseDir+"tmp/",introduction.getName()+".txt",list);
                //FileUtil.writeToFile(MyConfig.baseDir+"tmp/",introduction.getName()+".txt",list);
                bookPath = MyConfig.baseDir+"tmp/"+introduction.getName()+".txt";
                loaded = true;
            }
        }).start();

    }
}
