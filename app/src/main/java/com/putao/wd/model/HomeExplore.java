package com.putao.wd.model;

import java.io.Serializable;

/**
 * 探索--ViewPager数据
 * Created by yanghx on 2016/1/11.
 */
public class HomeExplore implements Serializable {

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
        return "HomeExplore{" +
                "imageUrl=" + imageUrl +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
