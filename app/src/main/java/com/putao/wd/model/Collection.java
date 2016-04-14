package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class Collection implements Serializable {
    private int type;//文章类型1文章2创意3话题4运营5活动
    private int id;
    private String title;
    private String subtitle;
    private String head_img;//封面图
    private String link_url;
    private String collect_time;//参与日期 yy/mm/dd 格式
    private Tag tags;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(String collect_time) {
        this.collect_time = collect_time;
    }

    public Tag getTags() {
        return tags;
    }

    public void setTags(Tag tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "type=" + type +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", head_img='" + head_img + '\'' +
                ", link_url='" + link_url + '\'' +
                ", collect_time='" + collect_time + '\'' +
                ", tags=" + tags +
                '}';
    }
}
