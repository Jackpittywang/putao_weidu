package com.sunnybear.library.model.http.callback;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sunnybear.library.BasicApplication;
import com.sunnybear.library.R;
import com.sunnybear.library.util.JsonUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 回调转换成json
 * Created by guchenkai on 2015/10/27.
 */
public abstract class JSONObjectCallback implements CacheCallback {
    public static final String TAG = JSONObjectCallback.class.getSimpleName();

    private static final int RESULT_SUCCESS = 0x01;//成功
    private static final int RESULT_FAILURE = 0x02;//失败
    //    private static final int RESULT_FINISH = 0x03;//完成
    private static final int RESULT_CACHE_SUCCESS = 0x03;//缓存回调成功

    private static final String KEY_URL = "url";
    private static final String KEY_JSON = "json";
    private static final String KEY_STATUS_CODE = "statusCode";
    private static final String KEY_FAILURE_MSG = "errorMsg";

    private Handler mHandler;//主线程回调

    public JSONObjectCallback() {
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RESULT_SUCCESS://成功
                        Bundle success = (Bundle) msg.obj;
                        String url_success = success.getString(KEY_URL);
                        String json = success.getString(KEY_JSON);
                        onSuccess(url_success, JSON.parseObject(json));
                        //请求完成
                        onFinish(url_success, true, "");
                        break;
                    case RESULT_CACHE_SUCCESS:
                        Bundle cache_success = (Bundle) msg.obj;
                        String cache_url_success = cache_success.getString(KEY_URL);
                        String cache_json = cache_success.getString(KEY_JSON);
                        onCacheSuccess(cache_url_success, JSON.parseObject(cache_json));
                        //请求完成
                        onFinish(cache_url_success, true, "");
                        break;
                    case RESULT_FAILURE://失败
                        Bundle failure = (Bundle) msg.obj;
                        String url_failure = failure.getString(KEY_URL);
                        int statusCode = failure.getInt(KEY_STATUS_CODE);
                        String failure_msg = failure.getString(KEY_FAILURE_MSG);
                        onFailure(url_failure, statusCode, failure_msg);
                        //请求完成
                        onFinish(url_failure, false, failure_msg);
                        break;
                }
            }
        };
    }

    /**
     * 网络请求成功回调
     *
     * @param url    网络地址
     * @param result 请求结果
     */
    public abstract void onSuccess(String url, JSONObject result);

    /**
     * 缓存请求成功回调
     *
     * @param url    网络地址
     * @param result 请求结果
     */
    public abstract void onCacheSuccess(String url, JSONObject result);

    /**
     * 请求失败回调
     *
     * @param url        网络地址
     * @param statusCode 状态码
     * @param msg        失败错误信息
     */
    public abstract void onFailure(String url, int statusCode, String msg);

    /**
     * 开始请求
     */
    public void onStart() {

    }

    /**
     * 完成请求
     *
     * @param url       url
     * @param isSuccess 请求是否成功
     * @param msg       请求完成的消息
     */
    public void onFinish(String url, boolean isSuccess, String msg) {

    }

    @Override
    public final void onResponse(Response response) throws IOException {
        if (response == null) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, "");
            bundle.putInt(KEY_STATUS_CODE, 404);
            bundle.putString(KEY_FAILURE_MSG, BasicApplication.getInstance().getResources().getString(R.string.not_network));
            mHandler.sendMessage(Message.obtain(mHandler, RESULT_FAILURE, bundle));
        }
        String url = response.request().urlString();
        String json = response.body().string();
        int statusCode = response.code();
        Logger.d(TAG, "url=" + url + ",状态码=" + statusCode);
        if (response.isSuccessful()) {
            Logger.d(TAG, "请求url:\n" + url + "\n" + "请求成功,请求结果=" + JsonUtils.jsonFormatter(json));
            if (!StringUtils.isEmpty(json)) {
                Bundle bundle = new Bundle();
                bundle.putString(KEY_URL, url);
                bundle.putString(KEY_JSON, json);
                mHandler.sendMessage(Message.obtain(mHandler, RESULT_SUCCESS, bundle));
            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, url);
            bundle.putInt(KEY_STATUS_CODE, statusCode);
            bundle.putString(KEY_FAILURE_MSG, "");
            mHandler.sendMessage(Message.obtain(mHandler, RESULT_FAILURE, bundle));
        }
    }

    @Override
    public final void onCacheResponse(Response response) throws IOException {
        if (response == null) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, "");
            bundle.putInt(KEY_STATUS_CODE, 404);
            bundle.putString(KEY_FAILURE_MSG, BasicApplication.getInstance().getResources().getString(R.string.not_network));
            mHandler.sendMessage(Message.obtain(mHandler, RESULT_FAILURE, bundle));
        }
        String url = response.request().urlString();
        String json = response.body().string();
        int statusCode = response.code();
        Logger.d(TAG, "url=" + url + ",状态码=" + statusCode);
        if (response.isSuccessful()) {
            Logger.d(TAG, "缓存请求url:\n" + url + "\n" + "缓存请求成功,请求结果=" + JsonUtils.jsonFormatter(json));
            if (!TextUtils.isEmpty(json)) {
                Bundle bundle = new Bundle();
                bundle.putString(KEY_URL, url);
                bundle.putString(KEY_JSON, json);
                mHandler.sendMessage(Message.obtain(mHandler, RESULT_CACHE_SUCCESS, bundle));
            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, url);
            bundle.putInt(KEY_STATUS_CODE, statusCode);
            bundle.putString(KEY_FAILURE_MSG, "");
            mHandler.sendMessage(Message.obtain(mHandler, RESULT_FAILURE, bundle));
        }
    }

    /**
     * 请求失败回调
     *
     * @param request request
     * @param e       异常
     */
    @Override
    public final void onFailure(Request request, IOException e) {
        String url = request.urlString();
        Logger.e(TAG, "url=" + url + "\n", e);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url);
        bundle.putInt(KEY_STATUS_CODE, 500);
        if (e instanceof SocketTimeoutException || e instanceof UnknownHostException)
            bundle.putString(KEY_FAILURE_MSG, "请检查网络后重新尝试");
        else
            bundle.putString(KEY_FAILURE_MSG, e.getMessage());
        mHandler.sendMessage(Message.obtain(mHandler, RESULT_FAILURE, bundle));
    }
}
