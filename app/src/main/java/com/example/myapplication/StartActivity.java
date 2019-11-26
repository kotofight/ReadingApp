package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Common.Style2Activity;
import com.example.myapplication.RequiestTest.RequestService;
import com.example.myapplication.RequiestTest.ResponseRec;
import com.example.myapplication.RequiestTest.RetrofitGet;
import com.example.myapplication.ResponseModel.User;
import com.example.myapplication.SQLiteDB.DBOperation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StartActivity extends AppCompatActivity {
    private Button begin_login;  // 开始登录按钮
    private EditText et_username;
    private EditText et_password;
    private DBOperation dbOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        begin_login  =  (Button) findViewById(R.id.begin_login);
        et_username = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.password);
        dbOperation = new DBOperation(StartActivity.this);
        begin_login.setOnClickListener( new View.OnClickListener(){
          public void onClick ( View v) {
              if(et_username.getText()!=null&&et_password.getText()!=null)
              {
                    String username=et_username.getText().toString().trim();
                    String password=et_password.getText().toString().trim();
                    login(username,password);

              }
              else {
                  Toast.makeText(StartActivity.this,"请检查是否输入邮箱和密码",Toast.LENGTH_SHORT).show();
              }
        }
       });

    }

    public void login(String account,String pswd)
    {
        Retrofit retrofit= RetrofitGet.getRetrofit();
        RequestService requestService = retrofit.create(RequestService.class);
        Call<ResponseRec<User>> call = requestService.getCall(account,pswd);
        call.enqueue(new Callback<ResponseRec<User>>() {
            @Override
            public void onResponse(Call<ResponseRec<User>> call, Response<ResponseRec<User>> response) {

                ResponseRec<User> responseRec= response.body();
                User user=responseRec.getData();
                if(responseRec.getStatus()==1)
                {
                    Toast.makeText(StartActivity.this,responseRec.getMsg(),Toast.LENGTH_SHORT).show();
                }
                else if(responseRec.getStatus()==0)
                {
                    dbOperation.add(user);
                    Intent intent=new Intent(StartActivity.this, Style2Activity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseRec<User>> call, Throwable t) {

                Toast.makeText(StartActivity.this,"访问失败,请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
