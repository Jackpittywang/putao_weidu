package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ProductStatus implements Serializable {

    private int status;
    private int has_special;
    private String h5;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHas_special() {
        return has_special;
    }

    public void setHas_special(int has_special) {
        this.has_special = has_special;
    }

    public String getH5() {
        return h5;
    }

    public void setH5(String h5) {
        this.h5 = h5;
    }

    @Override
    public String toString() {
        return "ProductStatus{" +
                "status=" + status +
                ", has_special=" + has_special +
                ", h5='" + h5 + '\'' +
                '}';
    }
}
