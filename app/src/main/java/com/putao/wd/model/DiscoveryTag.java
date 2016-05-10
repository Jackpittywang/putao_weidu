package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class DiscoveryTag implements Serializable{
    private String title;
    private boolean isEqualsTag;

    public boolean isEqualsTag() {
        return isEqualsTag;
    }

    public void setIsEqualsTag(boolean isEqualsTag) {
        this.isEqualsTag = isEqualsTag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "DiscoveryTag{" +
                "isEqualsTag=" + isEqualsTag +
                ", title='" + title + '\'' +
                '}';
    }
}
