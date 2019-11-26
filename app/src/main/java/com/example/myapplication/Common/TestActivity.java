package com.example.myapplication.Common;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.FileUtil.FileFunc;
import com.example.myapplication.FileUtil.LoadUpThread;
import com.example.myapplication.R;
import com.example.myapplication.SQLiteDB.DBOperation;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce1);
        Button button = findViewById(R.id.start_read);
        textView = findViewById(R.id.intro_intro);
        ImageView imageView = findViewById(R.id.intro_imgview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,1000);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK)
        {
            assert data != null;
            Uri uri=data.getData();
            assert uri != null;
            String filePath = FileFunc.getPath(this, uri);
            textView.setText(filePath);
            new LoadUpThread(new LoadUpThread.UploadLisener() {
                @Override
                public void isOk(String msg) {
                    System.out.println(msg);
                }

                @Override
                public void isError(String msg) {
                    System.out.println(msg);
                }
            },filePath, new DBOperation(this).getUserID()).start();
            Log.i("本地路径", "onActivityResult: "+ getApplicationContext().getFilesDir().getAbsolutePath());
            //获取文件名
            // fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());

        }
    }

}
