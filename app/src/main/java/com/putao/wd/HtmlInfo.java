package com.putao.wd;

import android.text.SpannableStringBuilder;

import java.io.Serializable;

/**
 * Created by guchenkai on 2015/12/9.
 */
public class HtmlInfo implements Serializable {
    private SpannableStringBuilder builder;
    private boolean isLineFeed;

    public SpannableStringBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(SpannableStringBuilder builder) {
        this.builder = builder;
    }

    public boolean isLineFeed() {
        return isLineFeed;
    }

    public void setIsLineFeed(boolean isLineFeed) {
        this.isLineFeed = isLineFeed;
    }
}
