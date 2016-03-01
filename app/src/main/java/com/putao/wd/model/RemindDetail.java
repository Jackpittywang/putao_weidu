package com.putao.wd.model;

import java.io.Serializable;

/**
 * 消息详情
 * Created by Administrator on 2015/12/25.
 */
public class RemindDetail implements Serializable {
    private int location;//  0无跳转1内部跳转2外部跳转
    private String content;//显示内容
    private String img_url;//图片地址
    private String link_type;//explore探索文章  idea创想文章 product精选商品
    private String url;//跳转地址
    private String create_time;//创建时间戳

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLink_type() {
        return link_type;
    }

    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "RemindDetail{" +
                "location='" + location + '\'' +
                ", content='" + content + '\'' +
                ", img_url='" + img_url + '\'' +
                ", link_type='" + link_type + '\'' +
                ", url='" + url + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
