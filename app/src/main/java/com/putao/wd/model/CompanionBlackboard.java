package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class CompanionBlackboard implements Serializable {
//    private String response;
//    private String date;
//    private String imageurl;
//    private String title;
//    private String content;
//    private int count;
//    private boolean showData;
//
//    //话题视图的 标签 话题征集
//    private int type;
//
//    //活动视图的进行中
//    private int isrun;
//    //活动视图的活动时间
//    private String time_quantum;
//
//
//    public String getResponse() {
//        return response;
//    }
//
//    public void setResponse(String response) {
//        this.response = response;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getImageurl() {
//        return imageurl;
//    }
//
//    public void setImageurl(String imageurl) {
//        this.imageurl = imageurl;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }
//
//    public boolean isShowData() {
//        return showData;
//    }
//
//    public void setShowData(boolean showData) {
//        this.showData = showData;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public int getIsrun() {
//        return isrun;
//    }
//
//    public void setIsrun(int isrun) {
//        this.isrun = isrun;
//    }
//
//    public String getTime_quantum() {
//        return time_quantum;
//    }
//
//    public void setTime_quantum(String time_quantum) {
//        this.time_quantum = time_quantum;
//    }
//
//    public CompanionBlackboard(String response, String date, String imageurl, String title, String content, int count, int type, int isrun, String time_quantum) {
//        this.response = response;
//        this.date = date;
//        this.imageurl = imageurl;
//        this.title = title;
//        this.content = content;
//        this.count = count;
//        this.type = type;
//        this.isrun = isrun;
//        this.time_quantum = time_quantum;
//    }

    private int type;
    private int id;
    private String time;
    private String icon;
    private String subtitle;
    private int like_count;
    private int attend_count;
    private String start_time;
    private String end_time;

    private List tags;

    private String http_code;
    private String msg;

    private boolean showDate;

    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAttend_count() {
        return attend_count;
    }

    public void setAttend_count(int attend_count) {
        this.attend_count = attend_count;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getHttp_code() {
        return http_code;
    }

    public void setHttp_code(String http_code) {
        this.http_code = http_code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isShowDate() {
        return showDate;
    }

    public void setShowDate(boolean showDate) {
        this.showDate = showDate;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
