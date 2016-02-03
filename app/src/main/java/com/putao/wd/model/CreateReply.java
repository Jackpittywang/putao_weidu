package com.putao.wd.model;

import java.util.List;

/**
 * 创造回复
 * Created by Administrator on 2015/2/3
 */
public class CreateReply extends Page {
    private String id;
    private String username;
    private String avatar;
    private String real_avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getReal_avatar() {
        return real_avatar;
    }

    public void setReal_avatar(String real_avatar) {
        this.real_avatar = real_avatar;
    }

    @Override
    public String toString() {
        return "CreateReply{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", real_avatar='" + real_avatar + '\'' +
                '}';
    }
}
