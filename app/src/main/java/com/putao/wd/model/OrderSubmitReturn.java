package com.putao.wd.model;

import java.io.Serializable;

/**
 * 订单提交后的返回数据
 *
 * Created by yanghx on 2015/12/29.
 */
public class OrderSubmitReturn implements Serializable {

    private String order_id;
    private String order_sn;
    private String price;
    private String time;
    private String limit_time;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLimit_time() {
        return limit_time;
    }

    public void setLimit_time(String limit_time) {
        this.limit_time = limit_time;
    }

    @Override
    public String toString() {
        return "OrderSubmitReturn{" +
                "order_id='" + order_id + '\'' +
                ", order_sn='" + order_sn + '\'' +
                ", price='" + price + '\'' +
                ", time='" + time + '\'' +
                ", limit_time='" + limit_time + '\'' +
                '}';
    }
}
