package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 订单列表
 * Created by wango on 2015/12/7.
 */
@Deprecated
public class OrderListItem implements Serializable {
    private String id;
    private String title;
    private String color;
    private String size;
    private String money;
    private String count;
    private String imgUrl;

    private String sum_count;
    private String sum_price;
    private String carriage;

    public String getCarriage() {
        return carriage;
    }

    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }

    private String sum;

    public String getSum_count() {
        return sum_count;
    }

    public void setSum_count(String sum_count) {
        this.sum_count = sum_count;
    }

    public String getSum_price() {
        return sum_price;
    }

    public void setSum_price(String sum_price) {
        this.sum_price = sum_price;
    }


    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


}
