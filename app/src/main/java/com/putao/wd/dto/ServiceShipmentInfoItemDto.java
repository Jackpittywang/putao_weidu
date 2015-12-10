package com.putao.wd.dto;

/**售后物流
 * Created by yanguoqiang on 15/12/4.
 */
public class ServiceShipmentInfoItemDto {

    public boolean isShowTopLine() {
        return showTopLine;
    }

    public void setShowTopLine(boolean showTopLine) {
        this.showTopLine = showTopLine;
    }

    private boolean showBottomLine;

    private boolean showTopLine;

    public boolean isShowHighLightImage() {
        return showHighLightImage;
    }

    public void setShowHighLightImage(boolean showHighLightImage) {
        this.showHighLightImage = showHighLightImage;
    }

    public boolean isShowBottomLine() {
        return showBottomLine;
    }

    public void setShowBottomLine(boolean showBottomLine) {
        this.showBottomLine = showBottomLine;
    }

    public String getShipmentInfo() {
        return shipmentInfo;
    }

    public void setShipmentInfo(String shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
    }

    /**
     * 第一个显示紫色的亮点，其它灰色的点
     */

    private boolean showHighLightImage;

    /**
     * 快递信息
     */
    private String shipmentInfo;


}
