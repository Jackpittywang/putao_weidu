package com.putao.wd.model;

import java.io.Serializable;

/**
 * 更改的产品--管理产品（保存）
 * Created by guchenkai on 2015/12/8.
 */
public class ManagementEditProduct implements Serializable {
    private int product_id;//应用ID号
    private int status;//产品是否已受控，1受控0未受控

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManagementEditProduct{" +
                "product_id=" + product_id +
                ", status=" + status +
                '}';
    }
}
