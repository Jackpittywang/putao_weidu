package com.sunnybear.library.model.http.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunnybear.library.util.JsonUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;

import java.io.Serializable;

/**
 * FastJson解析json
 * Created by guchenkai on 2015/10/27.
 */
public abstract class FastJsonCallback<T extends Serializable> extends JSONObjectCallback {
    private Class<? extends Serializable> clazz;

    public FastJsonCallback(Class<? extends Serializable> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public final void onSuccess(String url, JSONObject result) {
        String data = result.getString("data");
        if (StringUtils.isEmpty(data)) {
            onSuccess(url, (T) new String());
            return;
        }
        JsonUtils.JsonType type = JsonUtils.getJSONType(data);
        switch (type) {
            case JSON_TYPE_OBJECT:
                onSuccess(url, (T) JSON.parseObject(data, clazz));
                break;
            case JSON_TYPE_ARRAY:
                onSuccess(url, (T) JSON.parseArray(data, clazz));
                break;
            case JSON_TYPE_ERROR:
                onFailure(url, -200, "json格式错误");
                Logger.e(JSONObjectCallback.TAG, "json格式错误,json=" + data);
                break;
        }
    }

    /**
     * 请求成功回调
     *
     * @param url    网络地址
     * @param result 请求结果
     */
    public abstract void onSuccess(String url, T result);
}
