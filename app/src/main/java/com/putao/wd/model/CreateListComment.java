package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 创造评论
 * Created by zhanghao on 2015/12/8.
 */
public class CreateListComment implements Serializable {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CreateListComment{" +
                "comment='" + count + '\'' +
                '}';
    }
}
