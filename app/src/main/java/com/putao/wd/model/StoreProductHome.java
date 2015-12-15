package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wango on 2015/12/15.
 */
public class StoreProductHome implements Serializable {
    private String total_page;
    private String current_page;
    private List<StoreProduct> data;

    public String getTotal_page() {
        return total_page;
    }

    public void setTotal_page(String total_page) {
        this.total_page = total_page;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<StoreProduct> getData() {
        return data;
    }

    public void setData(List<StoreProduct> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StoreProductHome{" +
                "total_page='" + total_page + '\'' +
                ", current_page='" + current_page + '\'' +
                ", data=" + data +
                '}';
    }
}
