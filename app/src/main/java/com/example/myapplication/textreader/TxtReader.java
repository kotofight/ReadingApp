package com.example.myapplication.textreader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class TxtReader extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt_reader);

        //点击按钮跳转至ListAllFileActivity活动中
        Button fileOpenBtn = (Button)findViewById(R.id.openFileBtn);
        fileOpenBtn.setOnClickListener(new OpenFileAction());
    }

    //菜单选择
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.removeItem(R.id.gb2312);
        menu.removeItem(R.id.utf8);
        return true;
    }
    //菜单点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                doAbout();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //点击“关于之后”页面所要显示的内容
    private void doAbout() {
        Dialog dialog = new AlertDialog.Builder(TxtReader.this).setTitle(
                R.string.aboutTitle).setMessage(R.string.aboutInfo)
                .setPositiveButton(R.string.aboutOK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                            }
                        }).create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //内部类OpenFileAction 实现接口View.OnClickListener
    class OpenFileAction implements View.OnClickListener
    {
        public void onClick(View v) {
            Intent in = new Intent(TxtReader.this, ListAllFileActivity.class);
            in.putExtra("msg",ListAllFileActivity.READ);
            startActivityForResult(in, 0);
        }
    }
}
