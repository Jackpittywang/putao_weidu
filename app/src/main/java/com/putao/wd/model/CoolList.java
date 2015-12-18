package com.putao.wd.model;

import java.io.Serializable;

/**
 * 赞列表条目信息
 * Created by yanghx on 2015/12/18.
 */
public class CoolList implements Serializable {

    private String user_id;//用户ID
    private String user_name;//用户名字
    private String user_profile_photo;//用户头像

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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
        return "CoolList{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_profile_photo='" + user_profile_photo + '\'' +
                '}';
    }
}
