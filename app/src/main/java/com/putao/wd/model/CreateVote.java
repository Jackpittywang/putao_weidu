package com.putao.wd.model;

import java.io.Serializable;

/**
 * 成长日记首页
 * Created by zhanghao on 2015/12/8.
 */
public class CreateVote implements Serializable {
    private int up;
    private int down;

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    @Override
    public String toString() {
        return "CreateVote{" +
                "up=" + up +
                ", down=" + down +
                '}';
    }


}
