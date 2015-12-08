package com.putao.wd.model;

import java.io.Serializable;

/**
 * 活动列表
 * Created by guchenkai on 2015/12/8.
 */
public class ActionList implements Serializable {
    private int id;//活动ID
    private String banner_url;//Banner_url
    private int start_time;//活动开始时间
    private int end_time;//活动结束时间
    private String location;//地理位置
    private String description;//内容
    private String status;//活动状态
    private int registration_number;//报名人数
    private String type;//活动类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        switch (status){
            case "ONGOING":
                return "进行中";
            case "CLOSE":
                return "截止";
            case "LOOKBACK":
                return "回顾";
        }
        return "";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(int registration_number) {
        this.registration_number = registration_number;
    }

    public String getType() {
        switch (status){
            case "TEXT":
                return "文字活动";
            case "NEWS":
                return "新闻活动";
        }
        return "";
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ActionList{" +
                "id=" + id +
                ", banner_url='" + banner_url + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", registration_number=" + registration_number +
                ", type='" + type + '\'' +
                '}';
    }
}
