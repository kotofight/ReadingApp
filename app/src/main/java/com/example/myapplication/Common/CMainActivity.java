package com.example.myapplication.Common;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Common.Adapter.CLoopViewAdapter;
import com.example.myapplication.Common.Listener.CpagerOnClickListener;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CMainActivity extends AppCompatActivity {

    private ViewPager viewPager;  //轮播图模块
    private int[] mImg;
    private int[] mImg_id;
    private String[] mDec;
    private ArrayList<ImageView> mImgList;
    private LinearLayout ll_dots_container;
    private TextView loop_dec;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;

    private ListView listView;
    private String[] book={"天龙八部","我在未来等你","龙族","我和我的祖国","那些年我们一起追过的女孩","火星撞地球","爱情三十六计","空心","我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccactivity_main);

        initLoopView();  //实现轮播图
        listView = findViewById(R.id.main_list_view);
        //创建适配器
        /**
         * @param context The current context. 当前的上下文
         * @param resource The resource ID for a layout file containing a TextView to use when
         *                 instantiating views.   listView子项布局的id
         * @param objects The objects to represent in the ListView.  适配的数据
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CMainActivity.this,android.R.layout.simple_list_item_1,book);
        //绑定适配器

    }

    private void initLoopView() {
        viewPager = (ViewPager)findViewById(R.id.loopviewpager);
        ll_dots_container = (LinearLayout)findViewById(R.id.ll_dots_loop);
        loop_dec = (TextView)findViewById(R.id.loop_dec);

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
            imageView = new ImageView(this);
            imageView.setBackgroundResource(mImg[i]);
            imageView.setId(mImg_id[i]);
            imageView.setOnClickListener(new CpagerOnClickListener(getApplicationContext()));
            mImgList.add(imageView);
            //加引导点
            dotView = new View(this);
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
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //下一条
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        }
                    });
                }
            }
        }.start();

    }
}

