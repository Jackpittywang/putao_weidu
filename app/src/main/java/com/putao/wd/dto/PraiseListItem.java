package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 点赞列表项
 * Created by wango on 2015/12/4.
 */
@Deprecated
public class PraiseListItem implements Serializable {
    private int id;
    private String userIconUrl;//用户头像
    private String username;//用户名
    private String userattr;//用户类型
    private String praiseCount;//点赞数
    private String userDetailUrl;//用户主页链接

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

    public String getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(String praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getUserDetailUrl() {
        return userDetailUrl;
    }

    public void setUserDetailUrl(String userDetailUrl) {
        this.userDetailUrl = userDetailUrl;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public PraiseListItem() {
    }


    @Override
    public String toString() {
        return "PraiseListItem{" +
                "id='" + id + '\'' +
                "userIconUrl='" + userIconUrl + '\'' +
                "username='" + username + '\'' +
                "userattr='" + userattr + '\'' +
                "praiseCount='" + praiseCount + '\'' +
                "userDetailUrl='" + userDetailUrl + '\'' +
                '}';
    }
}
