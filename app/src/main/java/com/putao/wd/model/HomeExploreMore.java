package com.putao.wd.model;

import java.io.Serializable;

/**
 * 更多内容
 * Created by yanghx on 2016/1/12.
 */
public class HomeExploreMore implements Serializable {

    private int imageUrl;
    private String title;
    private String content;
    private String comment;
    private String cool;

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCool() {
        return cool;
    }

    public void setCool(String cool) {
        this.cool = cool;
    }

    @Override
    public String toString() {
        return "HomeExploreMore{" +
                "imageUrl=" + imageUrl +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", comment='" + comment + '\'' +
                ", cool='" + cool + '\'' +
                '}';
    }

}
