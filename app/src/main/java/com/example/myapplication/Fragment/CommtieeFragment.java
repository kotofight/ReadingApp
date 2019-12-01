package com.example.myapplication.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.Common.Adapter.UsersAdapter;
import com.example.myapplication.Common.AddActivity;
import com.example.myapplication.Common.Bean.CommentDetail;
import com.example.myapplication.Common.Bean.Users;
import com.example.myapplication.Common.Listener.CommentListener;
import com.example.myapplication.R;
import com.example.myapplication.RequiestService.CommentTask;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 */
public class CommtieeFragment extends Fragment{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";

    private String mContentText;
    RecyclerView recyclerView;
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

        //initUsers();//初始化users数据
        recyclerView=rootView.findViewById(R.id.recycler_view);
        //将this修改为this.getActivity()
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //adapter=new UsersAdapter(usersList);
        //recyclerView.setAdapter(adapter);
        return rootView;
    }
    UsersAdapter adapter;
    boolean isGetData =false;
    @Override
    public void onResume() {
        if(!isGetData){
            initUsers();
            Log.i("消息","进入社区界面");
        }
        super.onResume();
    }

    //异步获取评论
    class MyTask extends AsyncTask<String,String,String>{
    List<CommentDetail> commentDetails = new ArrayList<>();
    List<Users> users = new ArrayList<>();
    int flag=0;
    @Override
    protected String doInBackground(String... strings) {
        new CommentTask().getComments(commentDetails, new CommentListener() {
            @Override
            public void checkStatus(String msg) {
            }
            @Override
            public void getComments(List<CommentDetail> comments) {
                usersList.clear();
                Log.i("消息","commentDetails"+comments.size());
                for(CommentDetail detail:comments){//为ListView填充评论
                    users.add(new Users(R.drawable.user1, detail.getUser().getUserName(), R.drawable.focus,detail.getComment().getContent(), detail.getComment().getTime(), "地球村", R.drawable.background, R.drawable.forward, R.string.forward, R.drawable.comment, R.string.comment, R.drawable.thumbup, R.string.thubmup));
                }
                usersList.addAll(users);
                adapter=new UsersAdapter(usersList);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                Log.i("消息","onPostExecute"+"  flag="+flag+"  list: "+users.toString());
            }
        });//获取评论列表
        Log.i("消息flag",":flag="+flag++);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
    //初始化用户数据函数
    private void initUsers(){
        new MyTask().execute();
        /*Users users1 = new Users(R.drawable.user1, "你好",R.drawable.focus,"一定要努力啊不试一试，怎么知道会不会成功呢不试一试，怎么知道会不会成功呢", "2019-10-10", "地址", R.drawable.background2, R.drawable.forward, R.string.forward, R.drawable.comment, R.string.comment, R.drawable.thumbup, R.string.thubmup);
        usersList.add(users1);
        Users users2 = new Users(R.drawable.user1, "你好", R.drawable.focus,"一定要努力啊不试一试，怎么知道会不会成功呢不试一试，怎么知道会不会成功呢", "2019-10-10", "地址", R.drawable.img_2, R.drawable.forward, R.string.forward, R.drawable.comment, R.string.comment, R.drawable.thumbup, R.string.thubmup);
        usersList.add(users2);
         */
    }
}
