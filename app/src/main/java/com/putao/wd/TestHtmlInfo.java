package com.putao.wd;

import java.io.Serializable;

/**
 * Created by guchenkai on 2015/12/8.
 */
public class TestHtmlInfo implements Serializable {
    private String color;
    private String size;
    private String title;
    private boolean isBold;//是否加粗
    private boolean isLineFeed;//是否换行

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setIsBold(boolean isBold) {
        this.isBold = isBold;
    }

    public boolean isLineFeed() {
        return isLineFeed;
    }

    public void setIsLineFeed(boolean isLineFeed) {
        this.isLineFeed = isLineFeed;
    }
}
