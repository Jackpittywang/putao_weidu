package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 产品详情
 * Created by guchenkai on 2015/12/8.
 */
public class ProductDetail implements Serializable {
    private String id;//商品id
    private String title;//商品标题
    private String subtitle;//商品子标题
    private String price;//价格
    private String describe;//图文混排
    private String share;//分享的地址
    private String attribute;//属性的值的名称
    private String attribute_name;//属性的项目名称
    private String services;//售后服务
    private String pack;//包装清单
    private String qt;
    private String current_spec;
    private List<String> pictures;//图片集

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
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute_name() {
        return attribute_name;
    }

    public void setAttribute_name(String attribute_name) {
        this.attribute_name = attribute_name;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getCurrent_spec() {
        return current_spec;
    }

    public void setCurrent_spec(String current_spec) {
        this.current_spec = current_spec;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", price='" + price + '\'' +
                ", describe='" + describe + '\'' +
                ", share='" + share + '\'' +
                ", attribute='" + attribute + '\'' +
                ", attribute_name='" + attribute_name + '\'' +
                ", services='" + services + '\'' +
                ", pack='" + pack + '\'' +
                ", qt='" + qt + '\'' +
                ", current_spec='" + current_spec + '\'' +
                ", pictures=" + pictures +
                '}';
    }
}
