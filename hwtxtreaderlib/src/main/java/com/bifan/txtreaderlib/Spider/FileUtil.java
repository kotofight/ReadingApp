package com.bifan.txtreaderlib.Spider;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtil {
    public static File writeToFile(String filePath, String fileName, List<Chapter> list){
        File dir = new File(filePath);
        if(!dir.exists())
            dir.mkdir();
        File file = new File(filePath,fileName);
        if(!file.exists()){
            try {
                Log.i("文件",file.getAbsolutePath());
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            file = new File(file,fileName);
            for(int i = 1;file.exists();i++){
                file = new File(filePath,fileName.substring(0,fileName.indexOf("."))+"("+i+")"+fileName.substring(fileName.indexOf(".")));
            }
            if(!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        write(file,false,list);
        return file;
    }
    public static void appendToFile(String filePath, String fileName, List<Chapter> list){
        File dir = new File(filePath);
        if(!dir.exists())
            dir.mkdir();
        File file = new File(filePath,fileName);
        if(!file.exists()){
            try {
                Log.i("文件",file.getAbsolutePath());
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        write(file,true,list);
    }
    private static void write(File file,boolean append,List<Chapter> list){
        FileWriter writer = null;
        Log.i("信息","文件开始写入本地");
        try {
            writer = new FileWriter(file,append);
            for(Chapter chapter:list){
                writer.write(chapter.getName());
                writer.write("\n");
                writer.write(chapter.getContent());
                writer.flush();
                Log.i("小说文件章节写入信息",chapter.getContent());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(writer!=null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
