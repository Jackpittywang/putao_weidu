package com.putao.wd.model;

import java.io.Serializable;

/**
 * 存储时间
 * Created by zhanghao on 2015/12/8.
 */
public class CacheTime implements Serializable {
    private long saveTime;

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    @Override
    public String toString() {
        return "CacheTime{" +
                "saveTime=" + saveTime +
                '}';
    }
}
