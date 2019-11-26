package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import com.example.myapplication.Common.Style2Activity;
import com.example.myapplication.SQLiteDB.DBOperation;

import androidx.appcompat.app.AppCompatActivity;


public class WelcomeActivity extends AppCompatActivity {

    private DBOperation dbOperation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        dbOperation = new DBOperation(WelcomeActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        },1200);
    }

    private boolean isdone = true;
    public void startMainActivity(){
        if (isdone) {
            isdone = false;
            if(dbOperation.islogin())
            {
                Intent intent = new Intent(WelcomeActivity.this, Style2Activity.class);
                startActivity(intent);
                finish();
            }
            else {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startMainActivity();
        return super.onTouchEvent(event);
    }
}
