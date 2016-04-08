package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Created by guchenkai on 2015/12/8.
 */
public class Tag implements Serializable {
    private int user_num;//用户数量
    private String postfix;//
    private String start_time;//活动开始
    private String end_time;//活动结束

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getUser_num() {
        return user_num;
    }

    public void setUser_num(int user_num) {
        this.user_num = user_num;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "end_time='" + end_time + '\'' +
                ", user_num=" + user_num +
                ", postfix='" + postfix + '\'' +
                ", start_time='" + start_time + '\'' +
                '}';
    }
}
