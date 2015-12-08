package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 报名列表
 * Created by wango on 2015/12/6.
 */
public class ApplyListItem  implements Serializable {
    private int id;
    private String userIconUrl;//用户头像
    private String username;//用户名
    private String userattr;//用户类型
    private String userDetailUrl;//用户主页链接

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserattr() {
        return userattr;
    }

    public void setUserattr(String userattr) {
        this.userattr = userattr;
    }

    public String getUserDetailUrl() {
        return userDetailUrl;
    }

    public void setUserDetailUrl(String userDetailUrl) {
        this.userDetailUrl = userDetailUrl;
    }

    public ApplyListItem() {
    }
    @Override
    public String toString() {
        return "PraiseListItem{" +
                "id='" + id + '\'' +
                "userIconUrl='" + userIconUrl + '\'' +
                "username='" + username + '\'' +
                "userattr='" + userattr + '\'' +
                "userDetailUrl='" + userDetailUrl + '\'' +
                '}';
    }
}
