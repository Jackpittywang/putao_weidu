package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProductDataDaily implements Serializable {
    private int spendTime;
    private int returnCount;
    private double percent;

    public int getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(int spendTime) {
        this.spendTime = spendTime;
    }

    public int getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(int returnCount) {
        this.returnCount = returnCount;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "ExploreProductDataDaily{" +
                "spendTime=" + spendTime +
                ", returnCount=" + returnCount +
                ", percent=" + percent +
                '}';
    }
}
