package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/6.
 */
public class CompanionBlackboard implements Serializable {
    private String response;
    private String date;
    private String imageurl;
    private String title;
    private String content;
    private int count;
    private boolean showData;

    //话题视图的 标签 话题征集
    private int type;

    //活动视图的进行中
    private int isrun;
    //活动视图的活动时间
    private String time_quantum;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isShowData() {
        return showData;
    }

    public void setShowData(boolean showData) {
        this.showData = showData;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsrun() {
        return isrun;
    }

    public void setIsrun(int isrun) {
        this.isrun = isrun;
    }

    public String getTime_quantum() {
        return time_quantum;
    }

    public void setTime_quantum(String time_quantum) {
        this.time_quantum = time_quantum;
    }

    public CompanionBlackboard(String response, String date, String imageurl, String title, String content, int count, int type, int isrun, String time_quantum) {
        this.response = response;
        this.date = date;
        this.imageurl = imageurl;
        this.title = title;
        this.content = content;
        this.count = count;
        this.type = type;
        this.isrun = isrun;
        this.time_quantum = time_quantum;
    }
}
