package com.example.myapplication.Listener;

import com.example.myapplication.Common.Bean.Book;

public interface DownloadListener {
    void startDownload(int size);
    void setDownloadPro(int position);
    void endDownload(Book book);
    void errorDownload();
}
