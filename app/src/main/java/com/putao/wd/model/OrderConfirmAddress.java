package com.putao.wd.model;

import java.io.Serializable;

/**
 * 填写订单--地址
 * Created by yanghx on 2015/12/28.
 */
public class OrderConfirmAddress implements Serializable {

    private String id;
    private String detail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "OrderConfirmAddress{" +
                "id='" + id + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
