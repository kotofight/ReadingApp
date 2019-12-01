package com.example.myapplication.Common;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Common.Bean.Comment;
import com.example.myapplication.Common.Bean.CommentDetail;
import com.example.myapplication.Common.Listener.CommentListener;
import com.example.myapplication.R;
import com.example.myapplication.RequiestService.CommentTask;
import com.example.myapplication.SQLiteDB.DBOperation;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //点击“取消”则返回到动态界面
        TextView cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(AddActivity.this, CommtieeFragment.class);
//                startActivity(intent);
                finish();
            }
        });
        //点击“发布”则发布活动
        final Button send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                comment.setUserid(new DBOperation(getApplicationContext()).getUserID());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = dateFormat.format(new java.util.Date());
                comment.setTime(time);
                comment.setContent(String.valueOf(editText.getText()));
                new CommentTask().addComment(comment, new CommentListener() {
                    @Override
                    public void checkStatus(String msg) {
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
                        finish();
                    }
                    @Override
                    public void getComments(List<CommentDetail> comments) {
                    }
                });
            }
        });
        //文本域的相应操作
        editText=findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //当文本域中的文字改变后，“发布”按钮透明度为“1”
                send.setAlpha(1);
            }
        });
        //上传图片
        ImageView postPicture=findViewById(R.id.postPicture);
        postPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
