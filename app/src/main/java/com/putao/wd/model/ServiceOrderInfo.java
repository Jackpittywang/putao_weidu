package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 售后数据--售后
 * Created by guchenkai on 2015/12/9.
 */
public class ServiceOrderInfo implements Serializable {

    private String id;
    private String order_sn;//订单号
    private String sell_type;
    private String uid;
    private int order_status;
    private String express_status;
    private int shipping_status;
    private int pay_status;
    private String pay_trade_no;
    private String pay_time;
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
    private String note;
    private String mall_ordercol;
    private String create_time;
    private String update_time;
    private String receive_shipping_time;
    private String order_status_msg;
    private String ip;
    private String product_money;
    private String express_money;
    private String source;
    private String pay_type;
    private String deliver_type;
    private int serviceValid;
    private float totalPrice;
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

    public String getExpress_status() {
        return express_status;
    }

    public void setExpress_status(String express_status) {
        this.express_status = express_status;
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

    public String getPay_trade_no() {
        return pay_trade_no;
    }

    public void setPay_trade_no(String pay_trade_no) {
        this.pay_trade_no = pay_trade_no;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMall_ordercol() {
        return mall_ordercol;
    }

    public void setMall_ordercol(String mall_ordercol) {
        this.mall_ordercol = mall_ordercol;
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

    public String getReceive_shipping_time() {
        return receive_shipping_time;
    }

    public void setReceive_shipping_time(String receive_shipping_time) {
        this.receive_shipping_time = receive_shipping_time;
    }

    public String getOrder_status_msg() {
        return order_status_msg;
    }

    public void setOrder_status_msg(String order_status_msg) {
        this.order_status_msg = order_status_msg;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getProduct_money() {
        return product_money;
    }

    public void setProduct_money(String product_money) {
        this.product_money = product_money;
    }

    public String getExpress_money() {
        return express_money;
    }

    public void setExpress_money(String express_money) {
        this.express_money = express_money;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
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
        return "ServiceOrderInfo{" +
                "id='" + id + '\'' +
                ", order_sn='" + order_sn + '\'' +
                ", sell_type='" + sell_type + '\'' +
                ", uid='" + uid + '\'' +
                ", order_status=" + order_status +
                ", express_status='" + express_status + '\'' +
                ", shipping_status=" + shipping_status +
                ", pay_status=" + pay_status +
                ", pay_trade_no='" + pay_trade_no + '\'' +
                ", pay_time='" + pay_time + '\'' +
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
                ", note='" + note + '\'' +
                ", mall_ordercol='" + mall_ordercol + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", receive_shipping_time='" + receive_shipping_time + '\'' +
                ", order_status_msg='" + order_status_msg + '\'' +
                ", ip='" + ip + '\'' +
                ", product_money='" + product_money + '\'' +
                ", express_money='" + express_money + '\'' +
                ", source='" + source + '\'' +
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
