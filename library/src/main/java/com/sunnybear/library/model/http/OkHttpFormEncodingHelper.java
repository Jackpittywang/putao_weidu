package com.sunnybear.library.model.http;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.model.http.callback.JSONObjectCallback;
import com.sunnybear.library.util.Logger;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 普通网络请求助手
 * Created by guchenkai on 2016/1/22.
 */
public class OkHttpFormEncodingHelper {
    private static final String TAG = OkHttpFormEncodingHelper.class.getSimpleName();

    private CacheType mCacheType = CacheType.NETWORK_ELSE_CACHE;
    private OkHttpClient mOkHttpClient;

    public OkHttpFormEncodingHelper() {
        if (mOkHttpClient == null)
            mOkHttpClient = BasicApplication.getOkHttpClient();
    }

    public static OkHttpFormEncodingHelper newInstance() {
        return new OkHttpFormEncodingHelper();
    }

    /**
     * 设置缓存策略
     *
     * @param cacheType 缓存策略
     * @return OkHttpFormEncodingHelper实例
     */
    public OkHttpFormEncodingHelper cacheType(CacheType cacheType) {
        mCacheType = cacheType;
        return this;
    }

    /**
     * 添加拦截器
     *
     * @param interceptor 拦截器
     * @return OkHttpFormEncodingHelper实例
     */
    public OkHttpFormEncodingHelper addInterceptor(Interceptor interceptor) {
        mOkHttpClient.networkInterceptors().add(interceptor);
        return this;
    }

    /**
     * 网络请求
     *
     * @param request  请求实例
     * @param callback 请求回调
     */
    private void requestFromNetwork(Request request, Callback callback) {
        Logger.d(TAG, "读取网络信息,Url=" + getUrl(request));
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 缓存请求
     *
     * @param request  请求实例
     * @param callback 请求回调
     */
    private void requestFromCache(Request request, Callback callback) {
        Response response = getResponse(mOkHttpClient.getCache(), request);
        if (response != null)
            try {
                Logger.d(TAG, "读取缓存信息,Url=" + getUrl(request));
                callback.onResponse(response);
            } catch (IOException e) {
                callback.onFailure(request, e);
                Logger.e(e);
            }
        else
            callback.onFailure(request, new IOException("response没有本地缓存"));
    }

    /**
     * 反射方法取得响应体
     *
     * @param cache   缓存
     * @param request 请求体
     * @return 响应体
     */
    private Response getResponse(Cache cache, Request request) {
        Class clz = cache.getClass();
        try {
            Method get = clz.getDeclaredMethod("get", Request.class);
            get.setAccessible(true);
            return (Response) get.invoke(cache, request);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(e);
        }
        return null;
    }

    /**
     * 请求
     *
     * @param request  请求实例
     * @param callback 请求回调
     */
    public void request(final Request request, final JSONObjectCallback callback) {
        if (callback == null)
            throw new NullPointerException("请设置请求回调");
        callback.onStart();
        switch (mCacheType) {
            case NETWORK:
                requestFromNetwork(request, callback);
                break;
            case CACHE:
                requestFromCache(request, callback);
                break;
            case CACHE_ELSE_NETWORK:
                requestFromCache(request, new Callback() {
                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful())
                            callback.onResponse(response);
                        else
                            requestFromNetwork(request, callback);
                    }

                    @Override
                    public void onFailure(Request request, IOException e) {
                        requestFromNetwork(request, callback);
                    }
                });
                break;
            case NETWORK_ELSE_CACHE:
                requestFromNetwork(request, new Callback() {
                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful())
                            callback.onResponse(response);
                        else
                            requestFromCache(request, callback);
                    }

                    @Override
                    public void onFailure(Request request, IOException e) {
                        requestFromCache(request, callback);
                    }
                });
                break;
        }
    }

    /**
     * 获取Url
     *
     * @param request 请求体
     * @return url
     */
    private String getUrl(Request request) {
        return request.urlString();
    }
}
