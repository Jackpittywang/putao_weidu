package com.putao.wd.model;

import java.io.Serializable;

/**
 * 成长日记首页
 * Created by zhanghao on 2015/12/8.
 */
public class DiaryTitle implements Serializable {
    private String text;
    private String img;
    private String video;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "DiaryTitle{" +
                "text='" + text + '\'' +
                ", img='" + img + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
