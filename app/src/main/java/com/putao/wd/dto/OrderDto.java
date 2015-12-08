package com.putao.wd.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderDto implements Serializable {
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
    private List <OrderGoodsDto> goodsList;


    private String customerName;

    private String customerAddress;

    private String customerPhone;

    private List<OrderShipmentListItemDto> shipmentList;

    public List<OrderShipmentListItemDto> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<OrderShipmentListItemDto> shipmentList) {
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

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
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

    public List<OrderGoodsDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderGoodsDto> goodsList) {
        this.goodsList = goodsList;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }





}
