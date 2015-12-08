package com.putao.wd.model;

import java.io.Serializable;

/**
 * 产品详情
 * Created by guchenkai on 2015/12/8.
 */
public class ProductDetail implements Serializable {
    private String id;//商品id
    private String title;//商品标题
    private String Subtitle;//商品子标题
    private String Price;//价格
    private String Describe;//图文混排
    private String Share;//分享的地址
    private String Attribute;//属性的值的名称
    private String Attribute_name;//属性的项目名称
    private String Services;//售后服务
    private String Pack;//包装清单
    private String Pictures;//图片集

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

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getShare() {
        return Share;
    }

    public void setShare(String share) {
        Share = share;
    }

    public String getAttribute() {
        return Attribute;
    }

    public void setAttribute(String attribute) {
        Attribute = attribute;
    }

    public String getAttribute_name() {
        return Attribute_name;
    }

    public void setAttribute_name(String attribute_name) {
        Attribute_name = attribute_name;
    }

    public String getServices() {
        return Services;
    }

    public void setServices(String services) {
        Services = services;
    }

    public String getPack() {
        return Pack;
    }

    public void setPack(String pack) {
        Pack = pack;
    }

    public String getPictures() {
        return Pictures;
    }

    public void setPictures(String pictures) {
        Pictures = pictures;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", Subtitle='" + Subtitle + '\'' +
                ", Price='" + Price + '\'' +
                ", Describe='" + Describe + '\'' +
                ", Share='" + Share + '\'' +
                ", Attribute='" + Attribute + '\'' +
                ", Attribute_name='" + Attribute_name + '\'' +
                ", Services='" + Services + '\'' +
                ", Pack='" + Pack + '\'' +
                ", Pictures='" + Pictures + '\'' +
                '}';
    }
}
