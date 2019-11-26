package com.example.myapplication.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;
import com.leon.lib.settingview.LSettingItem;


public class SmeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_me);

        LSettingItem one =(LSettingItem)findViewById(R.id.item_one);
        one.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {

            public void click() {
                Toast.makeText(SmeActivity.this,"点击了钱包",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
