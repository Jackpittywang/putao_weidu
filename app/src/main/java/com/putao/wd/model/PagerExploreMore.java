package com.putao.wd.model;

import java.io.Serializable;

/**
 * 更多内容
 * Created by yanghx on 2016/1/12.
 */
public class PagerExploreMore implements Serializable {

    private int imageUrl;
    private String title;
    private String content;

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

    @Override
    public String toString() {
        return "PagerExploreMore{" +
                "imageUrl=" + imageUrl +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
