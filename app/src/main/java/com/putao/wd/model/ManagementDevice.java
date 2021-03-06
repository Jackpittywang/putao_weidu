package com.putao.wd.model;

import java.io.Serializable;

/**
 * 受控设备--管理产品
 * Created by guchenkai on 2015/12/8.
 */
public class ManagementDevice implements Serializable {
    private int slave_id;//受控设备id
    private String slave_device_id;//受控设备号
    private String slave_device_name;//device_name
    private String status;//设备是否已受控，1受控0未受控

    public int getSlave_id() {
        return slave_id;
    }

    public void setSlave_id(int slave_id) {
        this.slave_id = slave_id;
    }

    public String getSlave_device_id() {
        return slave_device_id;
    }

    public void setSlave_device_id(String slave_device_id) {
        this.slave_device_id = slave_device_id;
    }

    public String getSlave_device_name() {
        return slave_device_name;
    }

    public void setSlave_device_name(String slave_device_name) {
        this.slave_device_name = slave_device_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManagementDevice{" +
                "slave_id=" + slave_id +
                ", slave_device_id='" + slave_device_id + '\'' +
                ", slave_device_name='" + slave_device_name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
