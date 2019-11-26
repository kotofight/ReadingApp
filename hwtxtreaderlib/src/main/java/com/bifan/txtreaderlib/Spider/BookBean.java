package com.bifan.txtreaderlib.Spider;

public class BookBean {
    private String fileName;
    private int fileNum;

    public String getFileName() {
        return fileName;
    }

    public BookBean(String fileName, int fileNum) {
        this.fileName = fileName;
        this.fileNum = fileNum;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    @Override
    public String toString() {
        return "BookBean{" +
                "fileName='" + fileName + '\'' +
                ", fileNum=" + fileNum +
                '}';
    }
}
