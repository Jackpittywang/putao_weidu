package com.putao.wd.model;

import java.io.Serializable;

/**
 * 消息详情
 * Created by Administrator on 2015/12/25.
 */
public class NotifyDetail implements Serializable {
    private String mid;//跳转的参数id
    private String type;//跳转的type
    private String content;//显示内容
    private String img_url;//图片地址
    private String create_time;//创建时间戳

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "NotifyDetail{" +
                "mid='" + mid + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", img_url='" + img_url + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
