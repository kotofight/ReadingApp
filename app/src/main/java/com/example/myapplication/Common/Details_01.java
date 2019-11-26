package com.example.myapplication.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Common.Adapter.UsersAdapter;
import com.example.myapplication.Common.Bean.Users;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Details_01 extends AppCompatActivity implements UsersAdapter.clickinview{
    int flag=0;//用于切换背景墙上关注按钮中的文字“关注”/“已关注”。以及item中的关注图片的切换
    //下面三个变量均是为了实现点击事件
    protected ImageView userImage;
    protected ImageView look;
    protected  ListView listView;

    private List<Users> user1List=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wh2activity_details_01);
        initview();//初始化点击事件
        initUser1();//初始化user1数据
        UsersAdapter adapter=new UsersAdapter(Details_01.this,R.layout.wh3main_item,user1List,Details_01.this);
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //背景墙上的关注按钮点击事件
        final Button button_3=findViewById(R.id.button_3);
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(0==flag){
                    Toast.makeText(Details_01.this,"你已成功关注TA！",Toast.LENGTH_SHORT).show();
                    button_3.setText("已关注");
                    flag=1;
                }
                else{
                    Toast.makeText(Details_01.this,"取消关注成功！",Toast.LENGTH_SHORT).show();
                    button_3.setText("关注");
                    flag=0;
                }
            }
        });
    }
    private  void initview(){
        listView=findViewById(R.id.list_view);
        userImage=findViewById(R.id.usePicPath);
        look=findViewById(R.id.look);
    }

    @Override
    public void click_userPicPath(View view) {
        //点击用户头像使其跳转到该用户所对应的详情介绍界面
        Intent intent=new Intent(Details_01.this,Details_01.class);
        startActivity(intent);
    }

    @Override
    public void click_userName(View view) {
        //点击用户名称使其跳转到该用户所对应的详情介绍界面
        Intent intent=new Intent(Details_01.this,Details_01.class);
        startActivity(intent);
    }

    @Override
    public void click_look(View view) {
        ImageView look=(ImageView)view.findViewById(R.id.look);
        if(flag==0){
            look.setImageResource(R.drawable.looked);
            Toast.makeText(Details_01.this,"你已成功关注TA",Toast.LENGTH_SHORT).show();
            flag=1;
        }
        else{
            look.setImageResource(R.drawable.look);
            Toast.makeText(Details_01.this,"取消关注",Toast.LENGTH_SHORT).show();
            flag=0;
        }
        listView.deferNotifyDataSetChanged();
    }

    private void initUser1()
    {
        for(int i=0;i<4;i++)
        {
            Users user1=new Users(R.drawable.user1,"悦读APP官方","今天又是元气满满的一天呢。宜读书","2019-09-10","地址",R.drawable.background2,"200","12","100");
            user1List.add(user1);
            Users user2=new Users(R.drawable.user1,"悦读APP官方","什么都没有，所以要多看书啊。我滴神啊！","2019-09-01","地址",R.drawable.wh2background,"11","22","33");
            user1List.add(user2);
        }

    }
}
