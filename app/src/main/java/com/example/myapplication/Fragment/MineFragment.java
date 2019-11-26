package com.example.myapplication.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SQLiteDB.DBOperation;
import com.leon.lib.settingview.LSettingItem;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";

    private String mContentText;


    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static MineFragment newInstance(String param1) {
        MineFragment fragment = new MineFragment();
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

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.sactivity_me, container, false);
        //TextView contentTv = rootView.findViewById(R.id.content_tv);
        //contentTv.setText(mContentText);
        LSettingItem one =(LSettingItem)rootView.findViewById(R.id.item_one);
        Button button = rootView.findViewById(R.id.user_out);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DBOperation(getContext()).logout();
                getActivity().finish();
            }
        });
        TextView textView = rootView.findViewById(R.id.username);
        DBOperation db = new DBOperation(getContext());
        if(db.islogin()){
            textView.setText(new DBOperation(getContext()).getUserName());
        }
        one.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {

            public void click() {
                Toast.makeText(getContext(),"点击了钱包",Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

}

