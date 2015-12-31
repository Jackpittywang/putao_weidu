package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 售后--列表信息
 *
 * Created by yanghx on 2015/12/29.
 */
public class ServiceList implements Serializable {

    private String id;
    private String uid;
    private String service_type;//售后类型--1换货/2退货/3退款
    private String order_id;
    private String status;//售后状态
    private String reply;
    private String area_id;
    private String area;
    private String province_id;
    private String province;
    private String city_id;
    private String city;
    private String address;
    private String tel;
    private String mobile;
    private String consignee;
    private String postcode;
    private String user_express_company;
    private String user_express_id;
    private String system_express_company;
    private String system_express_id;
    private String create_time;
    private String update_time;
    private String sale_product;
    private String warehouse_id;
    private float saleTotalPrice;
    private int saleTotalQuantity;
    private String depiction;
    private List<ServiceProduct> product;
    private ServiceOrderInfo order_info;
    private List<Express> express;

    //=========非接口传递值===========
    private String statusText;//售后状态显示文本



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getUser_express_company() {
        return user_express_company;
    }

    public void setUser_express_company(String user_express_company) {
        this.user_express_company = user_express_company;
    }

    public String getUser_express_id() {
        return user_express_id;
    }

    public void setUser_express_id(String user_express_id) {
        this.user_express_id = user_express_id;
    }

    public String getSystem_express_company() {
        return system_express_company;
    }

    public void setSystem_express_company(String system_express_company) {
        this.system_express_company = system_express_company;
    }

    public String getSystem_express_id() {
        return system_express_id;
    }

    public void setSystem_express_id(String system_express_id) {
        this.system_express_id = system_express_id;
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

    public String getSale_product() {
        return sale_product;
    }

    public void setSale_product(String sale_product) {
        this.sale_product = sale_product;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public float getSaleTotalPrice() {
        return saleTotalPrice;
    }

    public void setSaleTotalPrice(float saleTotalPrice) {
        this.saleTotalPrice = saleTotalPrice;
    }

    public int getSaleTotalQuantity() {
        return saleTotalQuantity;
    }

    public void setSaleTotalQuantity(int saleTotalQuantity) {
        this.saleTotalQuantity = saleTotalQuantity;
    }

    public String getDepiction() {
        return depiction;
    }

    public void setDepiction(String depiction) {
        this.depiction = depiction;
    }

    public List<ServiceProduct> getProduct() {
        return product;
    }

    public void setProduct(List<ServiceProduct> product) {
        this.product = product;
    }

    public ServiceOrderInfo getOrder_info() {
        return order_info;
    }

    public void setOrder_info(ServiceOrderInfo order_info) {
        this.order_info = order_info;
    }

    public List<Express> getExpress() {
        return express;
    }

    public void setExpress(List<Express> express) {
        this.express = express;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return "ServiceList{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", service_type='" + service_type + '\'' +
                ", order_id='" + order_id + '\'' +
                ", status='" + status + '\'' +
                ", reply='" + reply + '\'' +
                ", area_id='" + area_id + '\'' +
                ", area='" + area + '\'' +
                ", province_id='" + province_id + '\'' +
                ", province='" + province + '\'' +
                ", city_id='" + city_id + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", mobile='" + mobile + '\'' +
                ", consignee='" + consignee + '\'' +
                ", postcode='" + postcode + '\'' +
                ", user_express_company='" + user_express_company + '\'' +
                ", user_express_id='" + user_express_id + '\'' +
                ", system_express_company='" + system_express_company + '\'' +
                ", system_express_id='" + system_express_id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", sale_product='" + sale_product + '\'' +
                ", warehouse_id='" + warehouse_id + '\'' +
                ", saleTotalPrice=" + saleTotalPrice +
                ", saleTotalQuantity=" + saleTotalQuantity +
                ", depiction='" + depiction + '\'' +
                ", product=" + product +
                ", order_info=" + order_info +
                ", express=" + express +
                '}';
    }

}
