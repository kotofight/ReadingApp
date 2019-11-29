package com.bifan.txtreaderlib.Spider;

public class Result {

    boolean Ok;
    String name;
    String path;
    Result result;
    public Result(){

    }
    public Result(boolean ok, String name, String path) {
        Ok = ok;
        this.name = name;
        this.path = path;
    }

    public boolean isOk() {
        return Ok;
    }

    public void setOk(boolean ok) {
        Ok = ok;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "Ok=" + Ok +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
