package com.putao.wd.model;

import java.io.Serializable;

/**
 * 广告banner列表
 * Created by guchenkai on 2015/12/3.
 */
public class Banner implements Serializable {
    private String title;//标题
    private String img_url;//Banner 图片地址
    private String numbering;//排序

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "title='" + title + '\'' +
                ", img_url='" + img_url + '\'' +
                ", numbering='" + numbering + '\'' +
                '}';
    }
}
