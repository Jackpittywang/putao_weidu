package com.putao.wd.model;

import java.io.Serializable;

/**
 * 活动标签
 * Created by guchenkai on 2015/12/8.
 */
public class ActionLabel implements Serializable {
    private int label_id;//活动标签id
    private String label_name;//活动标签名字

    public int getLabel_id() {
        return label_id;
    }

    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    @Override
    public String toString() {
        return "ActionLabel{" +
                "label_id=" + label_id +
                ", label_name='" + label_name + '\'' +
                '}';
    }
}
