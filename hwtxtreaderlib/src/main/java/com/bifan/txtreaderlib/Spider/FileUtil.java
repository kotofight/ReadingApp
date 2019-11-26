package com.bifan.txtreaderlib.Spider;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtil {
    public static void writeToFile(String filePath, String fileName, List<Chapter> list){
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
        write(file,false,list);
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
        try {
            writer = new FileWriter(file,append);
            for(Chapter chapter:list){
                writer.write(chapter.getName());
                writer.write("\n");
                writer.write(chapter.getContent());
                writer.flush();
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
