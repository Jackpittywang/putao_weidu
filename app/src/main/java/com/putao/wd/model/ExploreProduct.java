package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProduct implements Serializable {
    private int slave_device_id;//游戏id
    private int time;//时间
    private String day_summary;//简介
    private List<ExploreProductData> product_list;

    public int getSlave_device_id() {
        return slave_device_id;
    }

    public void setSlave_device_id(int slave_device_id) {
        this.slave_device_id = slave_device_id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDay_summary() {
        return day_summary;
    }

    public void setDay_summary(String day_summary) {
        this.day_summary = day_summary;
    }

    public List<ExploreProductData> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<ExploreProductData> product_list) {
        this.product_list = product_list;
    }

    @Override
    public String toString() {
        return "ExploreProduct{" +
                "slave_device_id=" + slave_device_id +
                ", time=" + time +
                ", day_summary='" + day_summary + '\'' +
                ", product_list=" + product_list +
                '}';
    }
}
