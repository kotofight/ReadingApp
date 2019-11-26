package com.example.myapplication.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Common.Details_01;
import com.example.myapplication.Common.Bean.Users;
import com.example.myapplication.Common.Adapter.UsersAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 */
public class CommtieeFragment extends Fragment implements UsersAdapter.clickinview {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";

    private String mContentText;

    int flag=0;//用户切换关注的图片
    //下面三个变量均是为了实现点击事件
    protected ImageView userImage;
    protected ImageView look;
    protected ListView listView;
    private List<Users> usersList=new ArrayList<>();
    public CommtieeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static CommtieeFragment newInstance(String param1) {
        CommtieeFragment fragment = new CommtieeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.wh2activity_main, container, false);
        //TextView contentTv = rootView.findViewById(R.id.content_tv);
        //contentTv.setText(mContentText);


        initview(rootView);//初始化点击事件
        initUsers();//初始化users数据
        UsersAdapter adapter=new UsersAdapter(getContext(),R.layout.wh3main_item,usersList,CommtieeFragment.this );
        final ListView listView=rootView.findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        return rootView;
    }

    //initview()函数
    private  void initview(View rootView){
        listView=rootView.findViewById(R.id.list_view);
        userImage=rootView.findViewById(R.id.usePicPath);
        look=rootView.findViewById(R.id.look);
    }
    @Override
    public void click_userPicPath(View view) {
        //int values=(int)view.getTag();//暂时没有用到
       // Users users=usersList.get(values);//暂时没有用到

        //点击用户头像使其跳转到该用户所对应的详情介绍界面
        Intent intent=new Intent(getContext(), Details_01.class);
        startActivity(intent);
    }
    @Override
    public void click_userName(View view) {
        //int values=(int)view.getTag();//暂时没有用到
        TextView textView1=(TextView)view.findViewById(R.id.userName);
        //点击用户名称使其跳转到该用户所对应的详情介绍界面
        Toast.makeText(getContext(),textView1.getText(),Toast.LENGTH_SHORT).show();

        //Intent intent=new Intent(getContext(),Details_01.class);
        //startActivity(intent);
    }
    @Override
    public void click_look(View view) {
       //int values=(int)view.getTag();//暂时没有用到
        //Users users=usersList.get(values);//暂时没有用到
        ImageView look=(ImageView)view.findViewById(R.id.look);
        //TextView textView1=(TextView)view.findViewById(R.id.userName);
        //look.setTag(values);
        if(flag==0){
            look.setImageResource(R.drawable.looked);
            Toast.makeText(getContext(),"你已成功关注TA！",Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(),textView1.getText(),Toast.LENGTH_SHORT).show();
            flag=1;
        }
        else{
            look.setImageResource(R.drawable.look);
            Toast.makeText(getContext(),"取消关注成功！",Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(),textView1.getText(),Toast.LENGTH_SHORT).show();
            flag=0;
        }
        listView.deferNotifyDataSetChanged();
    }
    //初始化用户数据函数
    private void initUsers()
    {
        Users user1=new Users(R.drawable.user1,"悦读APP官方","今天又是元气满满的一天呢。宜读书","2019-09-10","地址",R.drawable.background2,"200","12","100");
        usersList.add(user1);
        Users user2=new Users(R.drawable.user2,"书大白","什么都没有，所以要多看书啊。我滴神啊！","2019-09-01","地址",R.drawable.wh2background,"11","22","33");
        usersList.add(user2);
        Users user3=new Users(R.drawable.user3,"李大白","好好学习天天向上","2019-09-01","地址",R.drawable.background2,"19","22","33");
        usersList.add(user3);
        Users user4=new Users(R.drawable.user4,"佟湘玉","我滴神啊！我滴神啊！我滴神啊！","2019-09-01","地址",R.drawable.wh2background,"15","55","67");
        usersList.add(user4);
        Users user5=new Users(R.drawable.user5,"郭芙蓉","所以要多看书啊！","2019-09-01","地址",R.drawable.background2,"110","2","32");
        usersList.add(user5);

    }
}
