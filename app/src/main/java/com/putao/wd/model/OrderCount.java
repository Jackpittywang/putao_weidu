package com.putao.wd.model;

import java.io.Serializable;

/**
 * 订单数量提示
 * Created by guchenkai on 2015/12/28.
 */
public class OrderCount implements Serializable {
    private OrderCountDetail unpaid;//未支付
    private OrderCountDetail undelivery;//未发货
    private OrderCountDetail unCheck;//未收货
    private OrderCountDetail service;//售后处理中数量

    public OrderCountDetail getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(OrderCountDetail unpaid) {
        this.unpaid = unpaid;
    }

    public OrderCountDetail getUndelivery() {
        return undelivery;
    }

    public void setUndelivery(OrderCountDetail undelivery) {
        this.undelivery = undelivery;
    }

    public OrderCountDetail getUnCheck() {
        return unCheck;
    }

    public void setUnCheck(OrderCountDetail unCheck) {
        this.unCheck = unCheck;
    }

    public OrderCountDetail getService() {
        return service;
    }

    public void setService(OrderCountDetail service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "OrderCount{" +
                "unpaid=" + unpaid +
                ", undelivery=" + undelivery +
                ", unCheck=" + unCheck +
                ", service=" + service +
                '}';
    }
}
