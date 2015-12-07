package com.putao.wd.dto;

import java.io.Serializable;

/**
 * Created by yanguoqiang on 15/11/29.
 */
public class OrderGoodsDto implements Serializable {

    // 商品数量
    private int number = 0;

    // 商品名字
    private String name;

    // 规格
    private String specification;

    //商品价格
    private int price;

    // 是否是预售
    private boolean isPreSale;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public boolean isPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(boolean isPreSale) {
        this.isPreSale = isPreSale;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
