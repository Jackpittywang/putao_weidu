package com.putao.wd.model;

import java.io.Serializable;

/**
 * 规格项目--规格信息--规格信息
 * Created by guchenkai on 2015/12/9.
 */
public class ProductNormsSepcItem implements Serializable {
    private String id;//规格项目id
    private String name;//规格项目名称
    private String display;//显示方式:0->text;1->icon

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
        return "ProductNormsSepcItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
