package com.putao.wd.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class DiscoveryResource implements Serializable {
//
//    public List<String> headerPic;
//    public List<String> hotTagPic;
//    public List<Resou> resources;
//
//    public DiscoveryResource(List<String> headerPic, List<String> hotTagPic, List<Resou> resources) {
//        this.headerPic = headerPic;
//        this.hotTagPic = hotTagPic;
//        this.resources = resources;
//    }

    private String id;
    private String title;
    private String icon;
    private boolean is_top;
    private boolean is_recommend;
    private String tag;

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

    public boolean is_top() {
        return is_top;
    }

    public void setIs_top(boolean is_top) {
        this.is_top = is_top;
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
        return "DiscoveryResource{" +
                "icon='" + icon + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", is_top=" + is_top +
                ", is_recommend=" + is_recommend +
                ", tag='" + tag + '\'' +
                '}';
    }
}
