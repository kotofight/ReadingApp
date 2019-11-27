package com.example.myapplication.Listener;

import com.example.myapplication.Common.Bean.Book;

public interface DownloadListener {
    void startDownload(int index,int size);
    void setDownloadPro(int index,int position);
    void endDownload(int index,Book book);
    void errorDownload(int index);
}
