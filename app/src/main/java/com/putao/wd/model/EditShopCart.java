package com.putao.wd.model;

/**
 * 编辑购物车
 * Created by wango on 2015/12/17.
 */
public class EditShopCart {
    private String pid;
    private String qt;

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "EditShopCart{" +
                "pid=" + pid +
                ", qt=" + qt +
                '}';
    }
}
