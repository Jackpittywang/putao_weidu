package com.putao.wd.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 售后
 * Created by wango on 2015/12/10.
 */
@Deprecated
public class ServiceDto implements Serializable {
    // order号
    private String orderNo;

    // 购买时间
    private long purchaseTime;

    // 订单状态
    private int orderStatus;

    // 运费
    private int shipmentFee;


    // 总价
    private int totalCost;

    // 商品列表
    private List<ServiceGoodsDto> goodsList;


    private String customerName;

    private String customerAddress;

    private String customerPhone;

    private List<ServiceShipmentListItemDto> shipmentList;

    public List<ServiceShipmentListItemDto> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<ServiceShipmentListItemDto> shipmentList) {
        this.shipmentList = shipmentList;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public long getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(long purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public int getServiceStatus() {
        return orderStatus;
    }

    public void setServiceStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getShipmentFee() {
        return shipmentFee;
    }

    public void setShipmentFee(int shipmentFee) {
        this.shipmentFee = shipmentFee;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public List<ServiceGoodsDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ServiceGoodsDto> goodsList) {
        this.goodsList = goodsList;
    }

    public String getServiceNo() {
        return orderNo;
    }

    public void setServiceNo(String orderNo) {
        this.orderNo = orderNo;
    }





}
