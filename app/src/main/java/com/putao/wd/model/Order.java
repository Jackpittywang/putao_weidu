package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单
 * Created by guchenkai on 2015/12/9.
 */
public class Order implements Serializable {
    private int id;//订单id
    private String order_sn;//订单SN
    private int sell_type;//销售类型
    private int uid;//用户id
    private int shipping_status;//商品配送情况
    private int pay_status;//支付状态
    private String pay_trade_no;//支付订单号
    private int express_status;//erp和快递100和官网同步字段
    private int pay_time;//支付时间
    private String outer_order_sn;//外部订单号
    private int need_invoice;//是否需要开发票
    private int invoice_content;//发票类型
    private String invoice_title;//发票抬头
    private int invoice_type;//发票抬头类型
    private String consignee;//收货人
    private int area_id;//区ID
    private String area;//区
    private int province_id;//省ID
    private String province;//省
    private int city_id;//市ID
    private String city;//市
    private String address;//地址
    private String tel;//电话
    private String mobile;//手机
    private String total_amount;//订单总金额
    private String total_quantity;//订单商品总数量
    private String note;//订单备注
    private String mall_ordercol;//暂时无用
    private int create_time;//订单创建时间
    private int update_time;//订单更新时间
    private int receive_shipping_time;//收货时间,下一版本会移除
    private String order_status_msg;//暂时无用
    private String ip;//购买ip
    private String product_money;//货物金额
    private String express_money;//快递费用
    private String source;//来源
    private List<OrderProduct> product;//订单里的商品数据
    private int orderStatusID;//订单状态id
    private int orderActionID;//订单操作id
    private String pay_type;//支付方式
    private String deliver_type;//配送方式

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getSell_type() {
        return sell_type;
    }

    public void setSell_type(int sell_type) {
        this.sell_type = sell_type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public int getExpress_status() {
        return express_status;
    }

    public void setExpress_status(int express_status) {
        this.express_status = express_status;
    }

    public int getPay_time() {
        return pay_time;
    }

    public void setPay_time(int pay_time) {
        this.pay_time = pay_time;
    }

    public String getOuter_order_sn() {
        return outer_order_sn;
    }

    public void setOuter_order_sn(String outer_order_sn) {
        this.outer_order_sn = outer_order_sn;
    }

    public int getNeed_invoice() {
        return need_invoice;
    }

    public void setNeed_invoice(int need_invoice) {
        this.need_invoice = need_invoice;
    }

    public int getInvoice_content() {
        return invoice_content;
    }

    public void setInvoice_content(int invoice_content) {
        this.invoice_content = invoice_content;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public int getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(int invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
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

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public int getReceive_shipping_time() {
        return receive_shipping_time;
    }

    public void setReceive_shipping_time(int receive_shipping_time) {
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

    public List<OrderProduct> getProduct() {
        return product;
    }

    public void setProduct(List<OrderProduct> product) {
        this.product = product;
    }

    public int getOrderStatusID() {
        return orderStatusID;
    }

    public void setOrderStatusID(int orderStatusID) {
        this.orderStatusID = orderStatusID;
    }

    public int getOrderActionID() {
        return orderActionID;
    }

    public void setOrderActionID(int orderActionID) {
        this.orderActionID = orderActionID;
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", order_sn='" + order_sn + '\'' +
                ", sell_type=" + sell_type +
                ", uid=" + uid +
                ", shipping_status=" + shipping_status +
                ", pay_status=" + pay_status +
                ", pay_trade_no='" + pay_trade_no + '\'' +
                ", express_status=" + express_status +
                ", pay_time=" + pay_time +
                ", outer_order_sn='" + outer_order_sn + '\'' +
                ", need_invoice=" + need_invoice +
                ", invoice_content=" + invoice_content +
                ", invoice_title='" + invoice_title + '\'' +
                ", invoice_type=" + invoice_type +
                ", consignee='" + consignee + '\'' +
                ", area_id=" + area_id +
                ", area='" + area + '\'' +
                ", province_id=" + province_id +
                ", province='" + province + '\'' +
                ", city_id=" + city_id +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", mobile='" + mobile + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", total_quantity='" + total_quantity + '\'' +
                ", note='" + note + '\'' +
                ", mall_ordercol='" + mall_ordercol + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", receive_shipping_time=" + receive_shipping_time +
                ", order_status_msg='" + order_status_msg + '\'' +
                ", ip='" + ip + '\'' +
                ", product_money='" + product_money + '\'' +
                ", express_money='" + express_money + '\'' +
                ", source='" + source + '\'' +
                ", product=" + product +
                ", orderStatusID=" + orderStatusID +
                ", orderActionID=" + orderActionID +
                ", pay_type='" + pay_type + '\'' +
                ", deliver_type='" + deliver_type + '\'' +
                '}';
    }
}
