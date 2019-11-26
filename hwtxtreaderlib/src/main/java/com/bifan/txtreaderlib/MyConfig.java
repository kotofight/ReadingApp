package com.bifan.txtreaderlib;

import android.os.Environment;

public class MyConfig {
    public static final String url = "http://47.102.205.169:8080/";
    public static final String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/mybooks/";
    public static final String BookBaseUrl = "https://qxs.la";
    public static boolean loaded=false;
}
