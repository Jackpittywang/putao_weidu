package com.putao.wd.model;

import java.io.Serializable;

/**
 * 个人信息
 * Created by guchenkai on 2015/12/8.
 */
public class Profile implements Serializable {
    private int user_id;
    private String user_name;
    private String user_profile_photo;

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

    @Override
    public String toString() {
        return "Profile{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_profile_photo='" + user_profile_photo + '\'' +
                '}';
    }
}
