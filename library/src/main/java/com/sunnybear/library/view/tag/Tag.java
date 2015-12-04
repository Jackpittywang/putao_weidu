package com.sunnybear.library.view.tag;

import java.io.Serializable;

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
