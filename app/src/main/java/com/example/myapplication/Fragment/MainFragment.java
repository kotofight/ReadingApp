package com.example.myapplication.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Common.Adapter.CLoopViewAdapter;
import com.example.myapplication.Common.Listener.CpagerOnClickListener;
import com.example.myapplication.Common.Result2Activity;
import com.example.myapplication.FileUtil.FileFunc;
import com.example.myapplication.FileUtil.LoadUpThread;
import com.example.myapplication.R;
import com.example.myapplication.SQLiteDB.DBOperation;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;

    private ViewPager viewPager;  //轮播图模块
    private int[] mImg;
    private int[] mImg_id;
    private String[] mDec;
    private ArrayList<ImageView> mImgList;
    private LinearLayout ll_dots_container;
    private TextView loop_dec;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ListView listView;
    private String[] book={"天龙八部","我在未来等你","龙族","我和我的祖国","那些年我们一起追过的女孩","火星撞地球","爱情三十六计","空心","我"};
    //以下三项为搜索的控件
    private SearchView mSearchView;
    //private ImageView mDeleteButton;//搜索框中的删除按钮
   // private Button mBtnNext;//下一界面按钮

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
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

        View rootView = inflater.inflate(R.layout.ccactivity_main, container, false);

        initView2(rootView);//定义了搜索的相关事件
        initData();//初始化搜索的数据
        setListener();//搜索的监听事件
        initLoopView(rootView);  //实现轮播图
        listView = rootView.findViewById(R.id.main_list_view);
        //创建适配器
        /**
         * @param context The current context. 当前的上下文
         * @param resource The resource ID for a layout file containing a TextView to use when
         *                 instantiating views.   listView子项布局的id
         * @param objects The objects to represent in the ListView.  适配的数据
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,book);
        //绑定适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"测试数据",Toast.LENGTH_SHORT).show();
            }
        });
        iv1 = rootView.findViewById(R.id.iv1);
        iv2 = rootView.findViewById(R.id.iv2);
        iv3 = rootView.findViewById(R.id.iv3);
        iv4 = rootView.findViewById(R.id.iv4);
        //上传文件按钮
        Button button = rootView.findViewById(R.id.upload_button);
       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,1000);
            }
        });

        return rootView;
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
            final String filePath = FileFunc.getPath(getContext(), uri);
            String fileName = new File(filePath).getName();
            if(fileName.substring(fileName.indexOf(".")).contains("txt")){
                new AlertDialog.Builder(getContext()).setPositiveButton("上传", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new LoadUpThread(new LoadUpThread.UploadLisener() {
                            @Override
                            public void isOk(String msg) {
                                Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void isError(String msg) {
                                Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                            }
                        }, filePath, new DBOperation(getContext()).getUserID()).start();
                        Toast.makeText(getContext(),"文件正在上传!",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消",null).setTitle("提示").setMessage("是否上传？").create().show();

            }else {
                Toast.makeText(getContext(),"请选择TXT文件！",Toast.LENGTH_LONG).show();
            }
            //textView.setText(filePath);
            //Log.i("本地路径", "onActivityResult: "+ getContext().getApplicationContext().getFilesDir().getAbsolutePath());
            //获取文件名
            // fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        }
    }


    //以下为搜索的相关函数
    private void initView2(View rootView){
        //mBtnNext=rootView.findViewById(R.id.btn_next);
        mSearchView=rootView.findViewById(R.id.searchview);
        //mAutoCompleteTextView=mSearchView.findViewById(R.id.search_src_text);
        //mDeleteButton=mSearchView.findViewById(R.id.search_close_btn);
    }
    //搜索的初始化
    private void initData(){
        mSearchView.setIconifiedByDefault(false);//设置搜索图标是否显示在搜索框内
        //1:回车
        //2:前往
        //3:搜索
        //4:发送
        //5:下一項
        //6:完成
        mSearchView.setImeOptions(2);//设置输入法搜索选项字段，默认是搜索，可以是：下一页、发送、完成等
        mSearchView.setInputType(1);//设置输入类型
       // mSearchView.setMaxWidth(800);//设置最大宽度
        mSearchView.setQueryHint("输入小说的名字");//设置查询提示字符串
  mSearchView.setSubmitButtonEnabled(true);//设置是否显示搜索框展开时的提交按钮
    }
    //搜索的监听
    private void setListener(){
        //mBtnNext.setOnClickListener(this);

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                //LogUtil.e(MainActivity.class,"=====query="+query)
                System.out.println("---------------------");
                Intent i = new Intent(getContext(), Result2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("bookName",query);
                i.putExtra("bookName",bundle);
                startActivity(i);
                return false;
            }

            //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                //LogUtil.e(MainActivity.class,"=====newText="+newText);

                return false;
            }
        });
    }
 private void initLoopView(View rootView) {
     viewPager = rootView.findViewById(R.id.loopviewpager);
     ll_dots_container = rootView.findViewById(R.id.ll_dots_loop);
     loop_dec = rootView.findViewById(R.id.loop_dec);

     // 图片资源id数组
     mImg = new int[]{
             R.drawable.test3,
             R.drawable.test2,
             R.drawable.test3,
             R.drawable.test3,
             R.drawable.test3
     };

     // 文本描述
     mDec = new String[]{
             "第一张推荐图",
             "第二张推荐图",
             "第三张推荐图",
             "第四张推荐图",
             "第五张推荐图"
     };

     mImg_id = new int[]{
            1,
             2,
             3,
             4,
             5
     };

     // 初始化要展示的5个ImageView
     mImgList = new ArrayList<ImageView>();
     ImageView imageView;
     View dotView;
     LinearLayout.LayoutParams layoutParams;
     for(int i=0;i<mImg.length;i++){
         //初始化要显示的图片对象
         imageView = new ImageView(getContext());
         imageView.setBackgroundResource(mImg[i]);
         imageView.setId(mImg_id[i]);
         imageView.setOnClickListener(new CpagerOnClickListener(getActivity().getApplicationContext()));

         mImgList.add(imageView);
         //加引导点
         dotView = new View(getContext());
         dotView.setBackgroundResource(R.drawable.dot);
         layoutParams = new LinearLayout.LayoutParams(10,10);
         if(i!=0){
             layoutParams.leftMargin=10;
         }
         //设置默认所有都不可用
         dotView.setEnabled(false);
         ll_dots_container.addView(dotView,layoutParams);
     }

     ll_dots_container.getChildAt(0).setEnabled(true);
     loop_dec.setText(mDec[0]);
     previousSelectedPosition=0;
     //设置适配器
     viewPager.setAdapter(new CLoopViewAdapter(mImgList));
     // 把ViewPager设置为默认选中Integer.MAX_VALUE / t2，从十几亿次开始轮播图片，达到无限循环目的;
     int m = (Integer.MAX_VALUE / 2) %mImgList.size();
     int currentPosition = Integer.MAX_VALUE / 2 - m;
     viewPager.setCurrentItem(currentPosition);

     viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled(int i, float v, int i1) {

         }

         @Override
         public void onPageSelected(int i) {
             int newPosition = i % mImgList.size();
             loop_dec.setText(mDec[newPosition]);
             ll_dots_container.getChildAt(previousSelectedPosition).setEnabled(false);
             ll_dots_container.getChildAt(newPosition).setEnabled(true);
             previousSelectedPosition = newPosition;
         }

         @Override
         public void onPageScrollStateChanged(int i) {

         }
     });

     // 开启轮询
     new Thread(){
         public void run(){
             isRunning = true;
             while(isRunning){
                 try{
                     Thread.sleep(5000);
                     //下一条
                     getActivity().runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                         }
                     });

                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }             }
         }
     }.start();

 }
}










