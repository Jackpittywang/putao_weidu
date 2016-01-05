package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单里的商品数据--订单
 * Created by guchenkai on 2015/12/9.
 */
public class OrderProduct implements Serializable {
    private String id;//id
    private String order_id;//订单id
    private String product_id;//商品id
    private String product_number;//商品号
    private String icon;//商品图标
    private String product_name;//商品名称
    private String quantity;//商品数量
    private String price;//商品金额
    private String real_icon;
    private String sku;//规格
    private String created_time;//创建时间
    private String updated_time;//更新时间
    private int allowService;//申请售后返回字段,这个商品是否允许售后
    private int allowServiceQuantity;//申请售后返回字段,这个商品最多可以售后的数量
    private List<ServiceBackImage> serviceBackImage = new ArrayList<ServiceBackImage>();
    private String reason;
    private boolean isSelect;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReal_icon() {
        return real_icon;
    }

    public void setReal_icon(String real_icon) {
        this.real_icon = real_icon;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getAllowService() {
        return allowService;
    }

    public void setAllowService(int allowService) {
        this.allowService = allowService;
    }

    public int getAllowServiceQuantity() {
        return allowServiceQuantity;
    }

    public void setAllowServiceQuantity(int allowServiceQuantity) {
        this.allowServiceQuantity = allowServiceQuantity;
    }

    public List<ServiceBackImage> getServiceBackImage() {
        return serviceBackImage;
    }

    public void setServiceBackImage(List<ServiceBackImage> serviceBackImage) {
        this.serviceBackImage = serviceBackImage;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id='" + id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_number='" + product_number + '\'' +
                ", icon='" + icon + '\'' +
                ", product_name='" + product_name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", real_icon='" + real_icon + '\'' +
                ", sku='" + sku + '\'' +
                ", created_time='" + created_time + '\'' +
                ", updated_time='" + updated_time + '\'' +
                ", allowService=" + allowService +
                ", allowServiceQuantity=" + allowServiceQuantity +
                ", serviceBackImage=" + serviceBackImage +
                ", reason=" + reason +
                '}';
    }
}
