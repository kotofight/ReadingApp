package com.example.myapplication.Common.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Common.Bean.Users;
import com.example.myapplication.R;

import java.util.List;



public class UsersAdapter extends ArrayAdapter<Users> {
    private int recourseId;
    clickinview clickinview;//接口
    public UsersAdapter(Context context, int textViewRecourseId, List<Users>objects,clickinview clickinview){
        super(context,textViewRecourseId,objects);
        recourseId=textViewRecourseId;
        this.clickinview=clickinview;//接口
    }
    //接口
    public interface  clickinview{
        public void click_userPicPath(View view);//点击用户头像的点击事件
        public void click_userName(View view);//点击用户名称的点击事件
        public void click_look(View view);//点击关注图片的点击事件
    }
    //（为了实现点击事件）重写下面三个方法

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Users getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ImageView userPicPath;//个人头像
        TextView userName;// 发布人
        TextView datetime;// 发布时间
        TextView address;//发布地址
        ImageView look;//关注图片
        TextView content;// 趣事内容
        ImageView picPath;// 趣事图片路径
        //ImageView forwardImg;// 转发图片
        TextView forward;// 转发数量
       // ImageView commentImg;// 评论图片
        TextView comment;// 评论数量
        //ImageView thumbUpImg;// 点赞图片
        TextView thumbUp;// 点赞数量


        Users users=getItem(position);//获取当前项的Users实例
        View view=LayoutInflater.from(getContext()).inflate(recourseId,parent,false);

        userPicPath=(ImageView) view.findViewById(R.id.usePicPath);
        userName=(TextView)view.findViewById(R.id.userName);
        datetime=(TextView)view.findViewById(R.id.datetime);
        address=(TextView) view.findViewById(R.id.address);
        look=(ImageView) view.findViewById(R.id.look);
        content=(TextView)view.findViewById(R.id.content);
        picPath=(ImageView) view.findViewById(R.id.picPath);
        //forwardImg=(ImageView) view.findViewById(R.id.forwardImg);
        forward=(TextView)view.findViewById(R.id.forward);
        //commentImg=(ImageView) view.findViewById(R.id.commentImg);
        comment=(TextView)view.findViewById(R.id.comment);
        //thumbUpImg=(ImageView) view.findViewById(R.id.thumbUpImg);
        thumbUp=(TextView)view.findViewById(R.id.thumbUp);

        userPicPath.setImageResource(users.getUserPicPath());
        userName.setText(users.getUserName());
        datetime.setText(users.getDatetime());
        address.setText(users.getAddress());
        look.setImageResource(R.drawable.look);
        content.setText(users.getContent());
        picPath.setImageResource(users.getPicPath());
       // forwardImg.setImageResource(users.getForwardImg());
        forward.setText(users.getForward());
        //commentImg.setImageResource(users.getCommentImg());
        comment.setText(users.getComment());
        //thumbUpImg.setImageResource(users.getThumbUpImg());
        thumbUp.setText(users.getThumbUp());

        //实现用户头像、用户名称和关注图片的点击事件
        userPicPath.setTag(position);
        look.setTag(position);
        userName.setTag(position);
        //用户头像点击事件
        userPicPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickinview.click_userPicPath(v);
            }
        });
        //用户名称点击事件
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickinview.click_userName(v);
            }
        });
        //关注图片点击事件
        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickinview.click_look(v);
            }
        });
        return view;
    }
}
