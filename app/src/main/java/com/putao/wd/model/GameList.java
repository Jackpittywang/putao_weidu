package com.putao.wd.model;

import java.io.Serializable;

/**
 * 游戏玩法列表
 * Created by zhanghao on 2015/12/8.
 */
public class GameList implements Serializable {
    private boolean isShowChild;

    public boolean isShowChild() {
        return isShowChild;
    }

    public void setIsShowChild(boolean isShowChild) {
        this.isShowChild = isShowChild;
    }

}
