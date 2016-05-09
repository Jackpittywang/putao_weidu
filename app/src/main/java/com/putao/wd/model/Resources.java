package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/8.
 */
public class Resources implements Serializable {
    private String id;//文章id
    private String title;//标题
    private String icon;//文章icon
    private String tag;//所属标签
    private String sid;//所属服务号/订阅号
    private String linke_url;//跳转链接

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getLinke_url() {
        return linke_url;
    }

    public void setLinke_url(String linke_url) {
        this.linke_url = linke_url;
    }
}
