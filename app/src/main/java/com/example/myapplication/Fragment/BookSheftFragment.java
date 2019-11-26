package com.example.myapplication.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.example.myapplication.Common.Adapter.ShelfBookAdapter;
import com.example.myapplication.Common.Bean.Book;
import com.example.myapplication.FileUtil.FileFunc;
import com.example.myapplication.R;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BookSheftFragment extends Fragment {
    private static final String ARG_SHOW_TEXT = "text";
    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    SwipeRefreshLayout.OnRefreshListener listener;
    private DrawerLayout mDrawerLayout;
    private Book[]books={new Book("名人传",R.drawable.shelfbook1),new Book("我的大学",R.drawable.shelfbook2),
            new Book("青鸟",R.drawable.shelfbook3),new Book("红楼梦",R.drawable.shelfbook4)};
    private List<Book> booklist=new ArrayList<>();
    private ShelfBookAdapter adapter;
    public BookSheftFragment() {
    }
    public static BookSheftFragment newInstance(String param1) {
        BookSheftFragment fragment = new BookSheftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.book_shelf, container, false);
      /*  for(Book book:books){
            book.save();
        }*/
        //LitePal.getDatabase();
        //initBook();
        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ShelfBookAdapter(booklist);
        //为小说添加监听事件
        adapter.setMyOnItemClickListener(new ShelfBookAdapter.MyOnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                if(position==booklist.size()-1){//小说添加按钮
                    addBook();
                    return;
                }
                Book book = booklist.get(position);
                if(null!=book.getPath()&&""!=book.getPath()){
                    //进入小说阅读界面
                    //设置横屏显示
                    TxtConfig.saveIsOnVerticalPageMode(getContext(),false);
                    //设置默认字体大小
                    TxtConfig.saveTextSize(getContext(),42);
                    HwTxtPlayActivity.loadTxtFile(getContext(),booklist.get(position).getPath());
                }else {
                    Toast.makeText(getContext(),"文件不存在",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void OnItemLongClick(View view, int position) {
                if (position == booklist.size()-1) {//小说添加按钮
                    return;
                }
                //Toast.makeText(getContext(),"进行了长点击"+position,Toast.LENGTH_SHORT).show();
                delBook(booklist.get(position));
            }
        });
        recyclerView.setAdapter(adapter);
        //下拉刷新
        swipeRefreshLayout=rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBooks();
            }
        };
        //设置下拉刷新监听器，当触发了下拉刷新操作的时候就会回调这个监听器的OnRefresh()方法
        swipeRefreshLayout.setOnRefreshListener(listener);
        return rootView;
    }
    //具体刷新逻辑
    private  void refreshBooks(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initBook();
                        adapter.notifyDataSetChanged();
                        if(swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    //先清空booklist中的数据,再从数据库中读取小说
    private void initBook(){
        booklist.clear();
        //LitePal.getDatabase();
        booklist.addAll(LitePal.findAll(Book.class));
        booklist.add(new Book("本地添加",R.drawable.book3));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //LitePal.deleteAll(Book.class);
    }
    @Override
    public void onResume() {
        super.onResume();
        doRefresh();
    }

    //添加小说
    public void addBook(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,1000);
    }
    //删除小说
    public void delBook(final Book book){
        String s[]={"同时删除本地文件"};
        final boolean choice[]={true};
        //删除的对话框
        new AlertDialog.Builder(getActivity()).setTitle("是否移除该小说").setMultiChoiceItems(s, choice, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                choice[0]=isChecked;
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(choice[0]&&book.getPath()!=null&&book.getPath()!=""){
                    File file = new File(book.getPath());
                    if(file.exists()&&file.isFile()){
                        file.delete();
                    }
                }
                book.delete();
                doRefresh();
                Log.i("消息","书架删除小说");
                Toast.makeText(getContext(),"移除成功",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消",null).show();

    }
    //手动刷新
    private void doRefresh(){
        //刷新书架
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                refreshBooks();
            }
        });
    }
    //文件路径选择回调
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK)
        {
            assert data != null;
            Uri uri=data.getData();
            assert uri != null;
            String filePath = FileFunc.getPath(getContext(), uri);
            String fileName = new File(filePath).getName();
            Book book = new Book(fileName.substring(0,fileName.indexOf(".")),filePath);
            book.save();
            doRefresh();
        }
    }
}