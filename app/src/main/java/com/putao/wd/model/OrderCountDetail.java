package com.putao.wd.model;

import java.io.Serializable;

/**
 * 订单数量详情
 * Created by guchenkai on 2015/12/28.
 */
public class OrderCountDetail implements Serializable {
    private int num;
    private int type;
    private String desc;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderCountDetail{" +
                "num=" + num +
                ", type=" + type +
                ", desc='" + desc + '\'' +
                '}';
    }
}
