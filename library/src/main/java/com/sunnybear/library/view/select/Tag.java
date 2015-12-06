package com.sunnybear.library.view.select;

import java.io.Serializable;

/**
 * 标签实体
 * Created by guchenkai on 2015/12/4.
 */
public class Tag implements Serializable {
    private int id;
    private boolean isEnable = true;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", isEnable=" + isEnable +
                ", title='" + title + '\'' +
                '}';
    }
}
