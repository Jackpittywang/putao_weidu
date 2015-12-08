package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 管理产品
 * Created by guchenkai on 2015/12/8.
 */
public class Management implements Serializable {
    private int master_id;//控制设备id
    private int use_num;//每天使用次数（次）,0为不限
    private int use_time;//每天使用时间（分钟）,0为不限
    private List<ManagementDevice> slave_device_list;//受控设备列表
    private List<ManagementProduct> product_list;//产品列表

    public int getMaster_id() {
        return master_id;
    }

    public void setMaster_id(int master_id) {
        this.master_id = master_id;
    }

    public int getUse_num() {
        return use_num;
    }

    public void setUse_num(int use_num) {
        this.use_num = use_num;
    }

    public int getUse_time() {
        return use_time;
    }

    public void setUse_time(int use_time) {
        this.use_time = use_time;
    }

    public List<ManagementDevice> getSlave_device_list() {
        return slave_device_list;
    }

    public void setSlave_device_list(List<ManagementDevice> slave_device_list) {
        this.slave_device_list = slave_device_list;
    }

    public List<ManagementProduct> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<ManagementProduct> product_list) {
        this.product_list = product_list;
    }

    @Override
    public String toString() {
        return "Management{" +
                "master_id=" + master_id +
                ", use_num=" + use_num +
                ", use_time=" + use_time +
                ", slave_device_list=" + slave_device_list +
                ", product_list=" + product_list +
                '}';
    }
}
