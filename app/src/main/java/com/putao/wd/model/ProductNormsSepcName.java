package com.putao.wd.model;

import java.io.Serializable;

/**
 * 规格名称--规格信息--规格信息
 * Created by guchenkai on 2015/12/9.
 */
public class ProductNormsSepcName implements Serializable {
    private String id;//规格值名称的ID
    private String text;//规格值的名称
    private String icon;//规格值的名称

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
        return "ProduceNormsSepcName{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
