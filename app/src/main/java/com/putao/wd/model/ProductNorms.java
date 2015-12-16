package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 商品规格
 * Created by guchenkai on 2015/12/9.
 */
public class ProductNorms implements Serializable {
    private ProductNormsSepc spec;//规格信息
    private List<ProductNormsSku> sku;//规格对应的商品信息

    public ProductNormsSepc getSpec() {
        return spec;
    }

    public void setSpec(ProductNormsSepc spec) {
        this.spec = spec;
    }

    public List<ProductNormsSku> getSku() {
        return sku;
    }

    public void setSku(List<ProductNormsSku> sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "ProductNorms{" +
                "spec=" + spec +
                ", sku=" + sku +
                '}';
    }
}
