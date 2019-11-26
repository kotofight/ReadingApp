package com.bifan.txtreaderlib.tasks;

import android.util.Log;

import com.bifan.txtreaderlib.OneIntroduction;
import com.bifan.txtreaderlib.Spider.ChapterLoader;
import com.bifan.txtreaderlib.bean.Chapter;
import com.bifan.txtreaderlib.bean.TxtFileMsg;
import com.bifan.txtreaderlib.interfaces.IChapter;
import com.bifan.txtreaderlib.interfaces.ILoadListener;
import com.bifan.txtreaderlib.interfaces.IParagraphData;
import com.bifan.txtreaderlib.interfaces.ITxtTask;
import com.bifan.txtreaderlib.main.ParagraphData;
import com.bifan.txtreaderlib.main.TxtReaderContext;
import com.bifan.txtreaderlib.utils.ELogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextLoader  {
    private String tag = "FileDataLoadTask";
    private List<IChapter> chapters;
    private IParagraphData paragraphData;
    private MyThread loadThread = new MyThread();
    private TxtReaderContext readerContext;
    private ILoadListener callBack;
    public void load(String text, TxtReaderContext readerContext,ILoadListener callBack) {
        this.readerContext=readerContext;
        this.callBack=callBack;
        paragraphData=readerContext.getParagraphData();
        chapters=readerContext.getChapters();
        if(paragraphData==null||chapters==null){
            paragraphData=new ParagraphData();
            chapters=new ArrayList<>();
        }
        callBack.onMessage("start read text");
        ELogger.log(tag, "start read text");

        if(loadThread.getState()==Thread.State.WAITING){
            loadThread.notify();
        }else if(loadThread.getState()==Thread.State.NEW){
            loadThread.start();
        }
    }
    class MyThread extends Thread{
        @Override
        public synchronized void run() {
            while (true){
                try {
                    nextChapters(2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    sleep(8*1000);
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    void nextChapters(int num) throws IOException {
        if(null==OneIntroduction.introduction)
            Log.i("消息","为空");
        ChapterLoader loader = new ChapterLoader(OneIntroduction.introduction);
        List<com.bifan.txtreaderlib.Spider.Chapter> chapterList= loader.nextChapters(num);
        if(chapterList.size()==0)
            callBack.onMessage("已完结");
        String data;
        int index = 0;
        int chapterIndex = 0;
        TxtFileMsg fileMsg=new TxtFileMsg();
        fileMsg.PreCharIndex = 0;
        fileMsg.PreParagraphIndex=0;
        if(chapters.size()!=0){
            index = chapters.get(chapters.size()-1).getEndParagraphIndex();
            chapterIndex = chapters.get(chapters.size()-1).getIndex();
            fileMsg.PreCharIndex = chapterIndex-1;
            fileMsg.PreParagraphIndex=index+1;
        }
        readerContext.setFileMsg(fileMsg);
        if(chapterList.size()==0){
            chapterList.add(new com.bifan.txtreaderlib.Spider.Chapter("完结","已完结"));
            callBack.onMessage("已完结");
        }
        for(com.bifan.txtreaderlib.Spider.Chapter c:chapterList) {
            data = c.getName()+"\r\n"+c.getContent();
            if (data.length() > 0) {
                String[] s = data.split("\n",-1);
                for (String tmp : s) {
                    Log.i("小说",tmp);
                    if (tmp.length() > 0) {
                        IChapter chapter = compileChapter(tmp+"\r\n", paragraphData.getCharNum(), index, chapterIndex);
                        paragraphData.addParagraph(tmp+"\r\n");
                        if (chapter != null) {
                            chapterIndex++;
                            chapters.add(chapter);
                        }
                        index++;
                    }
                }

            }
        }
        initChapterEndIndex(chapters, paragraphData.getParagraphNum());
        readerContext.setParagraphData(paragraphData);
        readerContext.setChapters(chapters);
        ITxtTask txtTask = new TxtConfigInitTask();
        txtTask.Run(callBack, readerContext);

        //initChapterEndIndex(chapters, paragraphData.getParagraphNum());
    }

    private static final String ChapterPatternStr = "(^.{0,3}\\s*第)(.{1,9})[章节卷集部篇回](\\s*)";
    private IChapter compileChapter(String data, int chapterStartIndex, int ParagraphIndex, int chapterIndex) {
            if (data.trim().startsWith("第") || data.contains("第")) {
                Pattern p = Pattern.compile(ChapterPatternStr);
                Matcher matcher = p.matcher(data);
                while (matcher.find()) {
                    int startIndex = 0;
                    int endIndex = data.length();
                    IChapter c = new Chapter(chapterStartIndex, chapterIndex, data, ParagraphIndex, ParagraphIndex, startIndex, endIndex);
                    return c;
                }
            }
            return null;
    }
    private void initChapterEndIndex(List<IChapter> chapters, int paragraphNum) {
        if (chapters != null && chapters.size() > 0) {
            for (int i = 0, sum = chapters.size(); i < sum; i++) {
                int nextIndex = i + 1;
                IChapter chapter = chapters.get(i);
                if (nextIndex < sum) {
                    int startIndex = chapter.getStartParagraphIndex();
                    int endIndex = chapters.get(nextIndex).getEndParagraphIndex()- 1;
                    if (endIndex < startIndex) {
                        endIndex = startIndex;
                    }
                    chapter.setEndParagraphIndex(endIndex);
                } else {
                    int endIndex = paragraphNum - 1;
                    endIndex = endIndex < 0 ? 0 : endIndex;
                    chapter.setEndParagraphIndex(endIndex);
                }
            }
        }
    }
}
