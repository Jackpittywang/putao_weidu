package com.putao.wd.dto;

import java.util.List;

/**
 * Created by wagnou on 15/12/4.
 */
@Deprecated
public class ServiceShipmentDto {
    // 商品名字
    private String name;

    //商品图标url
    private String iconUrl;

    // 商品数量
    private int number;

    private List<String> shipmentInfoList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<String> getShipmentInfoList() {
        return shipmentInfoList;
    }

    public void setShipmentInfoList(List<String> shipmentInfoList) {
        this.shipmentInfoList = shipmentInfoList;
    }
}
