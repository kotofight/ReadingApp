package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button new_user;     // 新用户按钮
    private Button login;        // 登录按钮
    private Button register;           // 注册页面的注册按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*   点击新用户按钮切换页面到 activity_register */
        new_user =  (Button) findViewById(R.id.new_user);
        new_user.setOnClickListener( new View.OnClickListener(){
            public void onClick ( View v) {
                Intent intent = new Intent();
                /*    .this 前面的内容为本文件最顶部 package 后的内容   */
                /*     .class 前面的内容为要跳转到的页面的活动名称      */
                intent.setClass(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
         });

        /*   点击登录按钮切换页面到 activity_start    */
        login =  (Button) findViewById(R.id.login);
        login.setOnClickListener( new View.OnClickListener(){
            public void onClick ( View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,StartActivity.class);
                startActivity(intent);
            }
        });



}
}