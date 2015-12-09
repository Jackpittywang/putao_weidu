package com.putao.wd.base;

import com.putao.wd.R;
import com.squareup.okhttp.Request;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.FastJsonCallback;
import com.sunnybear.library.view.NavigationBar;

import java.io.Serializable;

import butterknife.Bind;

/**
 * 葡萄纬度的基础Fragment
 * Created by guchenkai on 2015/11/25.
 */
public abstract class PTWDFragment<App extends BasicApplication> extends BasicFragment<App> implements NavigationBar.ActionsListener {
    @Bind(R.id.navigation_bar)
    NavigationBar navigation_bar;

    /**
     * 网络请求(首先查找文件缓存,如果缓存有就不在进行网络请求)
     *
     * @param request  request主体
     * @param callback 请求回调(建议使用SimpleFastJsonCallback)
     */
    protected <T extends Serializable> void networkRequestCache(Request request, FastJsonCallback callback) {
        String url = request.urlString();
        T cacheData = (T) mDiskFileCacheHelper.getAsSerializable(url);
        if (cacheData != null) {
            callback.onSuccess(url, cacheData);
            return;
        }
        networkRequest(request, callback);
    }

    /**
     * 加入磁盘缓存
     *
     * @param url    url
     * @param result 缓存数据
     * @param <T>    缓存数据的类型
     */
    protected <T extends Serializable> void cacheEnterDisk(String url, T result) {
        mDiskFileCacheHelper.put(url, result);
    }

    /**
     * 添加标题栏
     */
    protected void addNavigation() {
        navigation_bar.setActionListener(this);
    }

    /**
     * 设置主标题文字
     *
     * @param text 标题文字
     */
    protected void setMainTitle(String text) {
        navigation_bar.setMainTitle(text);
    }

    /**
     * 设置主标题文字颜色
     *
     * @param color
     */
    protected void setMainTitleColor(int color) {
        navigation_bar.setMainTitleColor(color);
    }

    /**
     * 设置左标题文字
     *
     * @param text 标题文字
     */
    protected void setLeftTitle(String text) {
        navigation_bar.setLeftTitle(text);
    }

    /**
     * 设置左标题文字颜色
     *
     * @param color 颜色id
     */
    protected void setLeftTitleColor(int color) {
        navigation_bar.setLeftTitleColor(color);
    }

    /**
     * 设置右标题文字
     *
     * @param text 标题文字
     */
    protected void setRightTitle(String text) {
        navigation_bar.setRightTitle(text);
    }

    /**
     * 设置右标题文字颜色
     *
     * @param color 颜色id
     */
    protected void setRightTitleColor(int color) {
        navigation_bar.setRightTitleColor(color);
    }

    @Override
    public void onLeftAction() {
        onBackPressed();
    }

    @Override
    public void onRightAction() {

    }

    @Override
    public void onMainAction() {

    }
}
