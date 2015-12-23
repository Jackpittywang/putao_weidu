package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 管理产品
 * Created by guchenkai on 2015/12/8.
 */
public class Management implements Serializable {
    private String use_num;//每天使用次数（次）,0为不限
    private String use_time;//每天使用时间（分钟）,0为不限
    private String status;
    private List<ManagementDevice> slave_device_list;//受控设备列表
    private List<ManagementProduct> product_list;//产品列表

    public String getUse_num() {
        return use_num;
    }

    public void setUse_num(String use_num) {
        this.use_num = use_num;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                "use_num='" + use_num + '\'' +
                ", use_time='" + use_time + '\'' +
                ", status='" + status + '\'' +
                ", slave_device_list=" + slave_device_list +
                ", product_list=" + product_list +
                '}';
    }
}
