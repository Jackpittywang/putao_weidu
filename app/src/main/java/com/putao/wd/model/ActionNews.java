package com.putao.wd.model;

import java.io.Serializable;

/**
 * 活动列表
 * Created by guchenkai on 2015/12/8.
 */
public class ActionNews implements Serializable {
    private String id;//活动ID
    private String banner_url;//Banner_url
    private String title;//标题
    private String start_time;//活动开始时间
    private String end_time;//活动结束时间
    private String location;//地理位置
    private String label;//标签
    private String description;//内容
    private String status;//活动状态
    private String registration_number;//报名人数
    private String type;//活动类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ActionNews{" +
                "id='" + id + '\'' +
                ", banner_url='" + banner_url + '\'' +
                ", title='" + title + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", location='" + location + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", registration_number='" + registration_number + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
