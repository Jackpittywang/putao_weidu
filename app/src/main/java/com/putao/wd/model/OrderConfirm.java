package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 填写订单
 *
 * Created by yanghx on 2015/12/28.
 */
public class OrderConfirm implements Serializable {

    private List<OrderConfirmInvoices> invoices;
    private List<OrderConfirmProduct> product;
    private String shipping_fee;
    private OrderConfirmAddress address;

    public List<OrderConfirmInvoices> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<OrderConfirmInvoices> invoices) {
        this.invoices = invoices;
    }

    public List<OrderConfirmProduct> getProduct() {
        return product;
    }

    public void setProduct(List<OrderConfirmProduct> product) {
        this.product = product;
    }

    public String getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(String shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public OrderConfirmAddress getAddress() {
        return address;
    }

    public void setAddress(OrderConfirmAddress address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderConfirm{" +
                "invoices=" + invoices +
                ", product=" + product +
                ", shipping_fee='" + shipping_fee + '\'' +
                ", address=" + address +
                '}';
    }
}
