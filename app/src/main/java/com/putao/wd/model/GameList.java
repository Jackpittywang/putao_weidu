package com.putao.wd.model;

import java.io.Serializable;

/**
 * 游戏玩法列表
 * Created by zhanghao on 2015/12/8.
 */
public class GameList implements Serializable {
    private int headId;

    public int getHeadId() {
        return headId;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

}
