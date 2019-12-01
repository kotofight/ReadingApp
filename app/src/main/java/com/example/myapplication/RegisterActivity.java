package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.RequiestService.CheckEmail;
import com.example.myapplication.RequiestService.Register;
import com.example.myapplication.RequiestService.ResponseRec;
import com.example.myapplication.RequiestService.RetrofitGet;
import com.example.myapplication.ResponseModel.User;
import com.example.myapplication.ServiceAPI.RegisterCheck;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    /*   新增的 ！！！！！ */
    private TimeCount time;
    private Button get_email_number;    // 注册页面的获取验证码按钮
    private EditText et_email;
    private Button bt_register;
    private String emailCode;
    private EditText et_userName;
    private EditText et_password;
    private EditText et_passwordAgain;
    private EditText et_emailNumber;
    private EditText et_telephone;

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {                  //计时完毕时触发
            get_email_number.setText("获取验证码");
            get_email_number.setClickable(true);
        }
        @Override
        public void onTick(final long millisUntilFinished){    //计时过程显示
            get_email_number.setClickable(false);
            get_email_number.setText(millisUntilFinished /1000+"秒重新获取");
        }
    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 构造 CountDownTimer 对象
        time = new TimeCount(60000,1000);
        get_email_number = (Button) findViewById(R.id.get_email_number);   // 获取验证码按扭

            et_email = (EditText) findViewById(R.id.email);
            bt_register = (Button) findViewById(R.id.register);//注册按钮
            et_userName = (EditText) findViewById(R.id.userName);
            et_password = (EditText) findViewById(R.id.password);
            et_passwordAgain = (EditText) findViewById(R.id.password_again);
            et_telephone = (EditText) findViewById(R.id.telephone);
            et_emailNumber = (EditText) findViewById(R.id.email_number);
            bt_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Map<String,String> registerMap=getInfo();
                   boolean isOK= RegisterCheck.isEquel(registerMap,RegisterActivity.this);
                   System.out.println(emailCode);

                   if(isOK&emailCode!=null)
                   {
                       if (emailCode.equals(et_emailNumber.getText().toString().trim()))
                       {
                            doRegister(registerMap.get("userName"),registerMap.get("password"),registerMap.get("email"),registerMap.get("telephone"));

                       }
                       else
                       {
                           Toast.makeText(RegisterActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                       }
                   }
                }
            });
            get_email_number.setOnClickListener(new View.OnClickListener() {   // 点击获取验证码按钮，开始秒数倒计时
                @Override
                public void onClick(View v) {
                    //time.start();

                    String email=et_email.getText().toString().trim();
                    if (!email.equals(""))
                    {
                        Toast.makeText(RegisterActivity.this,"邮箱验证时间较长，请等待20秒左右",Toast.LENGTH_LONG).show();
                        checkEmail(email);
                    }
                    else {
                        Toast.makeText(RegisterActivity.this,"请输入邮箱",Toast.LENGTH_SHORT).show();
                    }


                }
        });

    }

    public Map<String,String> getInfo()
    {
        Map<String,String> registerMap=new HashMap();
        registerMap.put("userName",et_userName.getText().toString().trim());
        registerMap.put("password",et_password.getText().toString().trim());
        registerMap.put("passwordAgain",et_passwordAgain.getText().toString().trim());
        registerMap.put("telephone",et_telephone.getText().toString().trim());
        registerMap.put("email",et_email.getText().toString().trim());
        registerMap.put("emailNumber",et_emailNumber.getText().toString().trim());
        return registerMap;
    }

    public void checkEmail(final String email)
    {
        Retrofit retrofit= RetrofitGet.getRetrofit();
        CheckEmail checkEmail=retrofit.create(CheckEmail.class);
        Call<ResponseRec<String>> call=checkEmail.checkEmail(email);
        call.enqueue(new Callback<ResponseRec<String>>() {
            @Override
            public void onResponse(Call<ResponseRec<String>> call, Response<ResponseRec<String>> response) {
                ResponseRec<String> responseRec=response.body();
                Toast.makeText(RegisterActivity.this,responseRec.getMsg(),Toast.LENGTH_SHORT).show();
                if(responseRec.getStatus()==0)
                {
                    emailCode=responseRec.getData();
                    time.start();
                }
            }

            @Override
            public void onFailure(Call<ResponseRec<String>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"发送失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void doRegister(String userName,String password,String email,String telephone)
    {
        Retrofit retrofit=RetrofitGet.getRetrofit();
        final Register register=retrofit.create(Register.class);
        Call<ResponseRec<User>> call=register.doRegister(userName,password,email,telephone);
        call.enqueue(new Callback<ResponseRec<User>>() {
            @Override
            public void onResponse(Call<ResponseRec<User>> call, Response<ResponseRec<User>> response) {
                System.out.println("2312313");
                ResponseRec<User> responseRec=response.body();
                Toast.makeText(RegisterActivity.this,responseRec.getMsg(),Toast.LENGTH_SHORT).show();
                if(responseRec.getStatus()==0)
                {
                    goLogin();
                }
            }

            @Override
            public void onFailure(Call<ResponseRec<User>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"注册失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goLogin()
    {
        Intent intent=new Intent(RegisterActivity.this,StartActivity.class);
        startActivity(intent);
        finish();
    }
}
