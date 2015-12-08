package com.putao.wd.model;

import java.io.Serializable;

/**
 * 活动报名
 * Created by guchenkai on 2015/12/8.
 */
public class ActionEnrollment implements Serializable {
    private int user_id;//用户ID
    private String user_name;//用户名字
    private String user_profile_photo;//用户头像
    private String status;//审核状态

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_profile_photo() {
        return user_profile_photo;
    }

    public void setUser_profile_photo(String user_profile_photo) {
        this.user_profile_photo = user_profile_photo;
    }

    public String getStatus() {
        switch (status) {
            case "THROUGH":
                return "通过";
            case "IGNORE ":
                return "忽略";
            case "REFUSED ":
                return "拒绝";
        }
        return "";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ActionEnrollment{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_profile_photo='" + user_profile_photo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
