package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 售后
 * Created by guchenkai on 2015/12/9.
 */
public class Service implements Serializable {
    private List<ServiceSaleinfo> sale_service;//售后数据
    private List<Order> order_info;//售后相关联的订单数据
    private String showStatus;//状态描述

    public List<ServiceSaleinfo> getSale_service() {
        return sale_service;
    }

    public void setSale_service(List<ServiceSaleinfo> sale_service) {
        this.sale_service = sale_service;
    }

    public List<Order> getOrder_info() {
        return order_info;
    }

    public void setOrder_info(List<Order> order_info) {
        this.order_info = order_info;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    @Override
    public String toString() {
        return "Service{" +
                "sale_service=" + sale_service +
                ", order_info=" + order_info +
                ", showStatus='" + showStatus + '\'' +
                '}';
    }
}
