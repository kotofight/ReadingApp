package com.example.myapplication.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common.Adapter.UsersAdapter;
import com.example.myapplication.Common.AddActivity;
import com.example.myapplication.Common.Bean.Users;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 */
public class CommtieeFragment extends Fragment{
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

        //点击“+”则跳转至发布动态界面
        ImageView add1=rootView.findViewById(R.id.add1);
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
        //点击“<-”则返回到上一个界面
        //TextView contentTv = rootView.findViewById(R.id.content_tv);
        //contentTv.setText(mContentText);

//        UsersAdapter adapter=new UsersAdapter(getContext(),R.layout.wh3main_item,usersList,CommtieeFragment.this );
//        final ListView listView=rootView.findViewById(R.id.list_view);
//        listView.setAdapter(adapter);

        initUsers();//初始化users数据
        RecyclerView recyclerView=rootView.findViewById(R.id.recycler_view);
        //将this修改为this.getActivity()
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        UsersAdapter adapter=new UsersAdapter(usersList);
        recyclerView.setAdapter(adapter);
        return rootView;
    }





    //初始化用户数据函数
    private void initUsers(){
        Users users1 = new Users(R.drawable.user1, "你好",R.drawable.focus,"一定要努力啊不试一试，怎么知道会不会成功呢不试一试，怎么知道会不会成功呢", "2019-10-10", "地址", R.drawable.background2, R.drawable.forward, R.string.forward, R.drawable.comment, R.string.comment, R.drawable.thumbup, R.string.thubmup);
        usersList.add(users1);
        Users users2 = new Users(R.drawable.user1, "你好", R.drawable.focus,"一定要努力啊不试一试，怎么知道会不会成功呢不试一试，怎么知道会不会成功呢", "2019-10-10", "地址", R.drawable.img_2, R.drawable.forward, R.string.forward, R.drawable.comment, R.string.comment, R.drawable.thumbup, R.string.thubmup);
        usersList.add(users2);
    }
}
