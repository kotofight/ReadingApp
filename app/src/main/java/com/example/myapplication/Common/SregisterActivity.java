package com.example.myapplication.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class SregisterActivity extends AppCompatActivity {

    private TimeCount time;
    private Button get_email_number;    // 注册页面的获取验证码按钮

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        public void onFinish() {                  //计时完毕时触发
            get_email_number.setText("获取验证码");
            get_email_number.setClickable(true);
        }

        public void onTick(final long millisUntilFinished) {    //计时过程显示
            get_email_number.setClickable(false);
            get_email_number.setText(millisUntilFinished / 1000 + "秒重新获取");
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sactivity_register);
        // 构造 CountDownTimer 对象
        time = new TimeCount(60000, 1000);
        get_email_number = findViewById(R.id.get_email_number);   // 获取验证码按扭
        get_email_number.setOnClickListener(new View.OnClickListener() {   // 点击获取验证码按钮，开始秒数倒计时
            public void onClick(View v) {
                time.start();
            }
        });
    }
}
