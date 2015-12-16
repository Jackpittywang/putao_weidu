package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 规格信息--规格信息
 * Created by guchenkai on 2015/12/9.
 */
public class ProductNormsSepc implements Serializable {
    private String spec_item;//规格项目
    private String spec_name;//规格名称

    public String getSpec_item() {
        return spec_item;
    }

    public void setSpec_item(String spec_item) {
        this.spec_item = spec_item;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    @Override
    public String toString() {
        return "ProductNormsSepc{" +
                "spec_item='" + spec_item + '\'' +
                ", spec_name='" + spec_name + '\'' +
                '}';
    }
}
