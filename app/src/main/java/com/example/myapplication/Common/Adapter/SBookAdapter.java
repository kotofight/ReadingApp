package com.example.myapplication.Common.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Common.Bean.SBook;
import com.example.myapplication.R;

import java.util.List;

public class SBookAdapter extends ArrayAdapter<SBook> {
    private int newResourceId;

    public SBookAdapter(Context context, int resourceId, List<SBook> cityList){
        super(context, resourceId, cityList);
        newResourceId = resourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        SBook book = getItem (position);
        View view = LayoutInflater.from (getContext ()).inflate (newResourceId, parent, false);
        TextView bookName = view.findViewById (R.id.book_name);
        ImageView bookImage = view.findViewById (R.id.book_image);

        bookName.setText (book.getName ());
        bookImage.setImageResource (book.getImageId ());
        return view;
    }

}
