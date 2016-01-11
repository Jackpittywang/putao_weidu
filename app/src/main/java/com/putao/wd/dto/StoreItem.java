package com.putao.wd.dto;

import java.io.Serializable;

/**
 * Created by yanghx on 2016/1/11.
 */
@Deprecated
public class StoreItem implements Serializable {
    private int iamgeURL;

    public int getIamgeURL() {
        return iamgeURL;
    }

    public void setIamgeURL(int iamgeURL) {
        this.iamgeURL = iamgeURL;
    }

    @Override
    public String toString() {
        return "StoreItem{" +
                "iamgeURL=" + iamgeURL +
                '}';
    }

}
