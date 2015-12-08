package com.putao.wd.model;

import java.io.Serializable;

/**
 * 更改的受控设备--管理产品（保存）
 * Created by guchenkai on 2015/12/8.
 */
public class ManagementEditDevice implements Serializable {
    private int slave_id;//受控设备id
    private int status;//设备是否已受控，1受控0未受控

    public int getSlave_id() {
        return slave_id;
    }

    public void setSlave_id(int slave_id) {
        this.slave_id = slave_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManagementEditDevice{" +
                "slave_id=" + slave_id +
                ", status=" + status +
                '}';
    }
}
