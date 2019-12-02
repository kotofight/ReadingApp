package com.example.myapplication.Common;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Fragment.BookSheftFragment;
import com.example.myapplication.Fragment.CommtieeFragment;
import com.example.myapplication.Fragment.MainFragment;
import com.example.myapplication.Fragment.MineFragment;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Style2Activity extends AppCompatActivity {
    private ViewPager mViewPager;
    private RadioGroup mTabRadioGroup;


    //碎片及其适配器
    private List<Fragment> mFragments;//碎片列表
    private FragmentPagerAdapter mAdapter;//碎片适配器
//显示总布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myhome2_foot);

        initView();//调用下面的函数 Button button1=(Button)findViewById(R.id.);

    }


    private void initView() {



        // find view
        mViewPager = findViewById(R.id.fragment_vp);//导航栏上面的布局 也是一个碎片
        mTabRadioGroup = findViewById(R.id.tabs_rg);//导航栏的布局
        // init fragment
        mFragments = new ArrayList<>(4);//碎片列表

       // mFragments.add(BlankFragment.newInstance("今日"));
        mFragments.add(MainFragment.newInstance("test"));
        mFragments.add(BookSheftFragment.newInstance("记录"));
        mFragments.add(CommtieeFragment.newInstance("通讯录"));
        mFragments.add(MineFragment.newInstance("设置特色是他"));
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);//用碎片列表 调用了下面的函数
        mViewPager.setAdapter(mAdapter);//导航栏上面的布局 是用了 碎片适配器
        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);//导航栏上面的布局
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);//导航栏的布局
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);//导航栏上面的布局
    }
//导航栏上部改变监听
    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        //导航栏上面的布局
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            radioButton.setChecked(true);

            //将底部按钮设为真
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
//导航栏按钮监听
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        //导航栏的布局
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    mViewPager.setCurrentItem(i);//导航栏上面的布局
                    return;
                }
            }
        }
    };

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
//适配器在哪里使用了
        private List<Fragment> mList;//碎片列表

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }//初始化碎片适配器

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
        //返回碎片列表的个数
    }

}
