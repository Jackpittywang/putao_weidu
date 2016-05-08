package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/8.
 */
public class FindResource implements Serializable{
    private String id;
    private String title;
    private String icon;
    private String tag;
    private String sid;
    private String link_url;
    private boolean is_recommend;

    private boolean is_top;

    public boolean is_top() {
        return is_top;
    }

    public void setIs_top(boolean is_top) {
        this.is_top = is_top;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean is_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(boolean is_recommend) {
        this.is_recommend = is_recommend;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FindResource{" +
                "icon='" + icon + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", sid='" + sid + '\'' +
                ", link_url='" + link_url + '\'' +
                ", is_recommend=" + is_recommend +
                '}';
    }
}
