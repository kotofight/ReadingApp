package com.example.myapplication.Common;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bifan.txtreaderlib.Spider.BookSearch;
import com.bifan.txtreaderlib.Spider.Introduction;
import com.example.myapplication.Common.Adapter.BookAdapter;
import com.example.myapplication.Common.Bean.Preview;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private List<Preview> previewList=new ArrayList<>();
    private String bookName;
    //private List<Book> bookList=new ArrayList<>();
    private List<Introduction> bookList = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         ImageButton back_button,baidu_button,go_button;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookupresult1);

        back_button=(ImageButton)findViewById(R.id.backbutton);
        baidu_button=(ImageButton)findViewById(R.id.gobaidu);
        go_button=(ImageButton)findViewById(R.id.goleave);

        bookName = getIntent().getBundleExtra("bookName").get("bookName").toString();

        System.out.println("Acticity---------------------"+bookName);

        listView =(ListView) findViewById(R.id.list1view1);
        //以下是listview的代码
        MyTask myTask = new MyTask();
        myTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Introduction book=bookList.get(position);
                //Toast.makeText(ResultActivity.this,book.getName(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ResultActivity.this,Result2Activity.class);
                intent.putExtra("faceUrl",book.getFaceUrl());
                startActivity(intent);

            }
        });
        //跳转到百度搜索
        baidu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 =new Intent(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent2);
            }
        });
        //返回
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //异步线程，获取网络搜索的返回结果并在主UI中更新处理
    class MyTask extends AsyncTask<String ,Integer,List<Introduction>>{
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(List<Introduction> introductions) {
            BookAdapter adapter =new BookAdapter(ResultActivity.this,R.layout.book3_list,bookList);
            listView.setAdapter(adapter);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected List<Introduction> doInBackground(String... strings) {
            bookList = BookSearch.serachBook(bookName);
            return null;
        }
    }

}