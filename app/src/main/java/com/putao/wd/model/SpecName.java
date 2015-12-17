package com.putao.wd.model;

import java.io.Serializable;

/**
 * 规格名称
 * Created by guchenkai on 2015/12/17.
 */
public class SpecName implements Serializable {
    private String id;
    private String text;
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "SpecName{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
