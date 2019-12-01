package com.example.myapplication.Common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Common.Adapter.UsersAdapter;
import com.example.myapplication.Common.Bean.Users;
import com.example.myapplication.Fragment.CommtieeFragment;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Details_01 extends AppCompatActivity {
    boolean flag=true;//为点击关注而设置
    private List<Users> userNewsList=new ArrayList<>();
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wh2activity_details_01);
        textView = findViewById(R.id.myComment);
        initUserNew();
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        UsersAdapter adapter=new UsersAdapter(userNewsList);
        recyclerView.setAdapter(adapter);
        //点击详情界面中的返回图标，返回到动态界面
        ImageView details_back=findViewById(R.id.details_back);
        details_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Details_01.this, CommtieeFragment.class);
                startActivity(intent);
            }
        });
        //点击关注/取消关注
        final TextView textView=(TextView) findViewById(R.id.text1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==true){
                    textView.setText("已关注");
                    flag=false;
                }
                else{
                    textView.setText("加关注");
                    flag=true;
                }
            }
        });


    }
    private void initUserNew(){
       /* Bundle bundle = getIntent().getBundleExtra("key");
        bundle.getParcelableArrayList("list");
        ArrayList<Users> list = bundle.getParcelableArrayList("list");*/
       //List<Users> users = (List<Users>)getIntent().getSerializableExtra("t");
        for(String key:getIntent().getStringArrayListExtra("list")){
            Users users = (Users) getIntent().getSerializableExtra(key);
            userNewsList.add(users);
        }
        textView.setText(getIntent().getStringExtra("name"));
   /*     Users users1 = new Users(R.drawable.user1, "你好",R.drawable.focus,"一定要努力啊不试一试，怎么知道会不会成功呢不试一试，怎么知道会不会成功呢", "2019-10-10", "地址", R.drawable.background2, R.drawable.forward,R.string.forward, R.drawable.comment, R.string.comment, R.drawable.thumbup, R.string.thubmup);
        userNewsList.add(users1);
        Users users2 = new Users(R.drawable.user1, "你好", R.drawable.focus,"一定要努力啊不试一试，怎么知道会不会成功呢不试一试，怎么知道会不会成功呢", "2019-10-10", "地址", R.drawable.img_2, R.drawable.forward, R.string.forward, R.drawable.comment, R.string.comment, R.drawable.thumbup, R.string.thubmup);
        userNewsList.add(users2);*/
    }
}
