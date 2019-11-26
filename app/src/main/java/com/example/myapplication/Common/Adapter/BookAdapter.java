package com.example.myapplication.Common.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bifan.txtreaderlib.Spider.Introduction;
import com.example.myapplication.R;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Introduction> {
    private int resourceId;
    public  BookAdapter(Context context, int textViewResourceId,  List<Introduction> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Introduction book = getItem(position);
       // Book book =getItem(position);//获取当前Book实例
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
             view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
             viewHolder =new ViewHolder();
             viewHolder.bookName=(TextView) view.findViewById(R.id.online_book_name);

             viewHolder.bookThur =(TextView)  view.findViewById((R.id.online_book_author));
             view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }

        //ImageButton imageButton=(ImageButton) view.findViewById(R.id.book_image2);
        //TextView bookName =(TextView) view.findViewById(R.id.book_name);
        //TextView bookThor =(TextView)  view.findViewById((R.id.book_thor));

        viewHolder.bookName.setText(book.getName());
        viewHolder.bookThur.setText(book.getAuthor());
        return view;
    }
    class ViewHolder {
        TextView bookName;
        TextView bookThur;
    }
}