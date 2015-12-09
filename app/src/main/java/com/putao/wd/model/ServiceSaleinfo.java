package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 售后数据--售后
 * Created by guchenkai on 2015/12/9.
 */
public class ServiceSaleinfo implements Serializable {
    private int id;//售后id
    private int service_type;//售后类型
    private int order_id;//订单id
    private int status;//售后状态
    private String reply;//系统回复
    private String user_express_company;//用户快递公司
    private String user_express_id;//用户快递单号
    private String system_express_company;//系统快递公司
    private String system_express_id;//系统快递单号
    private String sale_product;//
    private String warehouse_id;//
    private List<ServiceProduct> product;//售后申请的商品数据

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getService_type() {
        return service_type;
    }

    public void setService_type(int service_type) {
        this.service_type = service_type;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
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

    public List<ServiceProduct> getProduct() {
        return product;
    }

    public void setProduct(List<ServiceProduct> product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ServiceSaleinfo{" +
                "id=" + id +
                ", service_type=" + service_type +
                ", order_id=" + order_id +
                ", status=" + status +
                ", reply='" + reply + '\'' +
                ", user_express_company='" + user_express_company + '\'' +
                ", user_express_id='" + user_express_id + '\'' +
                ", system_express_company='" + system_express_company + '\'' +
                ", system_express_id='" + system_express_id + '\'' +
                ", sale_product='" + sale_product + '\'' +
                ", warehouse_id='" + warehouse_id + '\'' +
                ", product=" + product +
                '}';
    }
}
