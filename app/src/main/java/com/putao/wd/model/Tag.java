package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Created by guchenkai on 2015/12/8.
 */
public class Tag implements Serializable {
    private int like_count;//点赞数量
    private int attend_count;//参与数量
    private int status;//活动状态0未开始,1进行中,2已结束
    private String start_time;//活动开始
    private String end_time;//活动结束

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

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "attend_count=" + attend_count +
                ", like_count=" + like_count +
                ", status=" + status +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
    }
}
