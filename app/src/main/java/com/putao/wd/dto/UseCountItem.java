package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 每日使用次数
 * Created by wango on 2015/12/9.
 */
public class UseCountItem implements Serializable {
    private String name;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String selected;

    @Override
    public String toString() {
        return "ProductItem{" +
                "name='" + name + '\'' +
                ", selected='" + selected + '\'' +
                '}';
    }
}
