package com.bifan.txtreaderlib.Spider.Lisener;

import com.bifan.txtreaderlib.Spider.Chapter;

import java.util.List;

public interface DownloadLisener {
    void downloadStart(int size);
    void downloadProgress(int progress,int size);
    void downloadEnd(List<Chapter> chapters);
    void downloadError();
}
