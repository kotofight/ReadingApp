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

public class PreviewAdapter extends ArrayAdapter<Introduction> {
    private int resourceId;
    public  PreviewAdapter(Context context, int textViewResourceId, List<Introduction> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Introduction preview =getItem(position);//获取当前Preview实例
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder =new ViewHolder();
            viewHolder.previewText=(TextView) view.findViewById(R.id.online_book_name);
            viewHolder.authorText=(TextView) view.findViewById(R.id.online_book_author) ;
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.previewText.setText(preview.getName());
        viewHolder.authorText.setText(preview.getAuthor());
        return view;
    }
    class ViewHolder {
        TextView previewText;
        TextView authorText;
    }
}