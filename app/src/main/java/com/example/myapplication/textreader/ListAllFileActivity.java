package com.example.myapplication.textreader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class ListAllFileActivity extends ListActivity {
    public static int UPLOAD = 0x001;
    public static int READ = 0x002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist);
        initFileList();
    }

    private void initFileList()
    {
        //获取扩展存储设备的文件目录
        File path = Environment.getExternalStorageDirectory();
        Log.i("文件路径",path.getAbsolutePath());
        File[] f = path.listFiles();
        fill(f);
    }
    //菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.removeItem(R.id.gb2312);
        menu.removeItem(R.id.utf8);
        return true;
    }
    //菜单
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
    //菜单
    private void doAbout() {
        Dialog dialog = new AlertDialog.Builder(ListAllFileActivity.this).setTitle(
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file = fileNameList.get(position);
        if (file.isDirectory())
        {
            File[] f = file.listFiles();
            fill(f);
        }
        else {
            int msg = getIntent().getIntExtra("msg",0);
            if(msg==READ){
               /* Intent intent = new Intent(ListAllFileActivity.this, Reader.class);
                bundle = new Bundle();
                bundle.putString(fileNameKey, file.getAbsolutePath());
                intent.putExtras(bundle);
                System.out.println("--------------------------------------up " );
                startActivityForResult(intent, 0);
                System.out.println("--------------------------------------down " );
                /*Intent intent = new Intent(ListAllFileActivity.class,)
                bundle.putString(fileNameKey, file.getAbsolutePath());
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);*/
            }else if(msg==UPLOAD){
                //LoadUpThread loadUpThread = new LoadUpThread(file.getAbsolutePath());
                //loadUpThread.start();
            }
            finish();
        }
    }

    private void fill(File[] files) {
        fileNameList = new ArrayList<File>();
        for (File file : files) {
            if (isValidFileOrDir(file)) {
                fileNameList.add(file);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileToStrArr(fileNameList));
        setListAdapter(adapter);

//        ListView listView=(ListView)findViewById(R.id.android_list);
//        listView.setAdapter(adapter);
    }

    /*����Ƿ�Ϊ�Ϸ����ļ����������Ƿ�Ϊ·��*/
    private boolean isValidFileOrDir(File file)
    {
        if (file.isDirectory()) {
            return true;
        }
        else {
            String fileName = file.getName().toLowerCase();
            if (fileName.endsWith(".txt")) {
                return true;
            }
        }
        return false;
    }


    private String[] fileToStrArr(List<File> fl)
    {
        ArrayList<String> fnList = new ArrayList<String>();
        for (int i = 0; i < fl.size(); i++) {
            String nameString = fl.get(i).getName();
            fnList.add(nameString);
        }
        return fnList.toArray(new String[0]);
    }


    /*�ļ��б�*/
    private List<File> fileNameList;
    private Bundle bundle;
    private String fileNameKey = "fileName";

}
