package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单
 * Created by guchenkai on 2015/12/9.
 */
public class Order implements Serializable {
    private String id;
    private String order_sn;
    private String sell_type;
    private String uid;
    private int order_status;
    private int shipping_status;
    private int pay_status;
    private String outer_order_sn;
    private String need_invoice;
    private String invoice_content;
    private String invoice_title;
    private String invoice_type;
    private String consignee;
    private String area_id;
    private String area;
    private String province_id;
    private String province;
    private String city_id;
    private String city;
    private String address;
    private String tel;
    private String mobile;
    private String total_amount;
    private String total_quantity;
    private String create_time;
    private String update_time;
    private List<OrderProduct> product;
    private String pay_type;
    private String deliver_type;
    private int serviceValid;
    private int totalPrice;
    private int totalQuantity;
    private int banner_control;
    private int orderStatusID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getSell_type() {
        return sell_type;
    }

    public void setSell_type(String sell_type) {
        this.sell_type = sell_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(int shipping_status) {
        this.shipping_status = shipping_status;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public String getOuter_order_sn() {
        return outer_order_sn;
    }

    public void setOuter_order_sn(String outer_order_sn) {
        this.outer_order_sn = outer_order_sn;
    }

    public String getNeed_invoice() {
        return need_invoice;
    }

    public void setNeed_invoice(String need_invoice) {
        this.need_invoice = need_invoice;
    }

    public String getInvoice_content() {
        return invoice_content;
    }

    public void setInvoice_content(String invoice_content) {
        this.invoice_content = invoice_content;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(String total_quantity) {
        this.total_quantity = total_quantity;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<OrderProduct> getProduct() {
        return product;
    }

    public void setProduct(List<OrderProduct> product) {
        this.product = product;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getDeliver_type() {
        return deliver_type;
    }

    public void setDeliver_type(String deliver_type) {
        this.deliver_type = deliver_type;
    }

    public int getServiceValid() {
        return serviceValid;
    }

    public void setServiceValid(int serviceValid) {
        this.serviceValid = serviceValid;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getBanner_control() {
        return banner_control;
    }

    public void setBanner_control(int banner_control) {
        this.banner_control = banner_control;
    }

    public int getOrderStatusID() {
        return orderStatusID;
    }

    public void setOrderStatusID(int orderStatusID) {
        this.orderStatusID = orderStatusID;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", order_sn='" + order_sn + '\'' +
                ", sell_type='" + sell_type + '\'' +
                ", uid='" + uid + '\'' +
                ", order_status=" + order_status +
                ", shipping_status=" + shipping_status +
                ", pay_status=" + pay_status +
                ", outer_order_sn='" + outer_order_sn + '\'' +
                ", need_invoice='" + need_invoice + '\'' +
                ", invoice_content='" + invoice_content + '\'' +
                ", invoice_title='" + invoice_title + '\'' +
                ", invoice_type='" + invoice_type + '\'' +
                ", consignee='" + consignee + '\'' +
                ", area_id='" + area_id + '\'' +
                ", area='" + area + '\'' +
                ", province_id='" + province_id + '\'' +
                ", province='" + province + '\'' +
                ", city_id='" + city_id + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", mobile='" + mobile + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", total_quantity='" + total_quantity + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", product=" + product +
                ", pay_type='" + pay_type + '\'' +
                ", deliver_type='" + deliver_type + '\'' +
                ", serviceValid=" + serviceValid +
                ", totalPrice=" + totalPrice +
                ", totalQuantity=" + totalQuantity +
                ", banner_control=" + banner_control +
                ", orderStatusID=" + orderStatusID +
                '}';
    }
}
