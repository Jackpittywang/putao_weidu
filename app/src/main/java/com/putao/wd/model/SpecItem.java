package com.putao.wd.model;

import java.io.Serializable;

/**
 * 商品规格
 * Created by guchenkai on 2015/12/17.
 */
public class SpecItem implements Serializable {
    private String id;
    private String name;
    private String display;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "SpecItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
