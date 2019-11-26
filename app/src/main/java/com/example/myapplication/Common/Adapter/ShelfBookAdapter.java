package com.example.myapplication.Common.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Common.Bean.Book;
import com.example.myapplication.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ShelfBookAdapter extends RecyclerView.Adapter<ShelfBookAdapter.ViewHolder> {
    private Context mContext;
    private List<Book> mBookList;
    static  class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView bookImage;
        TextView bookName;
        public  ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            bookImage=(ImageView)view.findViewById(R.id.book_image);
            bookName=(TextView)view.findViewById(R.id.book_name);
        }
    }
    public ShelfBookAdapter(List<Book> bookList){
        mBookList=bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.book_item,parent,false);
        return  new ViewHolder(view);
    }
    private MyOnItemClickListener listener;
    public void setMyOnItemClickListener(MyOnItemClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Book book=mBookList.get(position);
        holder.bookName.setText(book.getBookName());
        //holder.bookImage.setImageResource(book.getBookImage());
        //使用Glide加载图片可以防止因压缩图片导致的内存溢出
        Glide.with(mContext).load(book.getBookImage()).into(holder.bookImage);
        if(listener!=null){
            //单点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    listener.OnItemClick(holder.itemView,position);
                }
            });
            //长点击事件
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    listener.OnItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
    //点击监听接口
    public interface MyOnItemClickListener{
        //单点监听
        void OnItemClick(View view ,int position);
        void OnItemLongClick(View view ,int position);
    }
}
