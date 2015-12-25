package com.putao.wd.model;

import java.io.Serializable;

/**
 * 编辑商品上传时的List泛型bean
 *
 * Created by yanghx on 2015/12/25.
 */
public class CartEdit implements Serializable {

    private String pid;
    private String qt;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    @Override
    public String toString() {
        return "CartEdit{" +
                "pid='" + pid + '\'' +
                ", qt='" + qt + '\'' +
                '}';
    }
}
