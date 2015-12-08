package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 订单详情页面包裹列表数据对象
 * Created by yanguoqiang on 15/12/8.
 */
public class OrderShipmentListItemDto implements Serializable {
    /**
     * 标题 “包裹x”
     */
    private String title;

    /**
     * 包裹状态
     */
    private int status;

    /**
     * 包裹id
     */
    private String shipmentId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 根据不同的状态返回相应的字符串
     * 下面的状态是从接口定义里面获取
     * 0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态
     *
     * @return
     */
    public String getStatusString() {
        if (status == 0) return "在途中";
        else if (status == 1) return "已揽收";
        else if (status == 2) return "疑难";
        else if (status == 3) return "已签收";
        else if (status == 4) return "退签";
        else if (status == 5) return "同城派送中";
        else if (status == 6) return "退回";
        else if (status == 7) return "转单";
        return "";
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
}
