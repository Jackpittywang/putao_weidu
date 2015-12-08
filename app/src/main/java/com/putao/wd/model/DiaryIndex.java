package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class DiaryIndex implements Serializable {
    private int slave_device_id;//关联的设备唯一id
    private String device_name;//关联的设备名称
    private int time;//当前数据产生的日期
    private String day_summary;//当日总结文案
    private List<ExploreProduct> product_list;//产品列表

    public int getSlave_device_id() {
        return slave_device_id;
    }

    public void setSlave_device_id(int slave_device_id) {
        this.slave_device_id = slave_device_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
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

    public List<ExploreProduct> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<ExploreProduct> product_list) {
        this.product_list = product_list;
    }

    @Override
    public String toString() {
        return "DiaryIndex{" +
                "slave_device_id=" + slave_device_id +
                ", device_name='" + device_name + '\'' +
                ", time=" + time +
                ", day_summary='" + day_summary + '\'' +
                ", product_list=" + product_list +
                '}';
    }
}
