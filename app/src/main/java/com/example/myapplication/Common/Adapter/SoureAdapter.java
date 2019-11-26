package com.example.myapplication.Common.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bifan.txtreaderlib.Spider.BookBean;
import com.example.myapplication.R;

import java.util.List;

public class SoureAdapter extends ArrayAdapter<BookBean> {
    private int resourceId;
    public  SoureAdapter(Context context, int textViewResourceId, List<BookBean> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        BookBean soure =getItem(position);//获取当前Soure实例
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder =new ViewHolder();
            viewHolder.soureName=(TextView) view.findViewById(R.id.soure_name);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.soureName.setText(soure.getFileName());
        return view;
    }
    class ViewHolder {
        TextView soureName;

    }
}