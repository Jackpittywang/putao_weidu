package com.sunnybear.library.model.http.callback;

import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.LoadingHUD;

import java.io.Serializable;

/**
 * 简单封装FastJsonCallback
 * Created by guchenkai on 2015/11/17.
 */
public abstract class SimpleFastJsonCallback<T extends Serializable> extends FastJsonCallback<T> {
    private LoadingHUD loading;


    public SimpleFastJsonCallback(Class<? extends Serializable> clazz, LoadingHUD loading) {
        super(clazz);
        this.loading = loading;
    }


    @Override
    public void onFailure(String url, int statusCode, String msg) {
        Logger.e("请求错误:url=" + url + ",statusCode=" + statusCode + ",错误信息=" + msg);
        if (loading != null) loading.dismiss();
    }
}
