package com.sunnybear.library.model.http.callback;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 缓存回调
 * Created by guchenkai on 2016/1/25.
 */
public interface CacheCallback extends Callback {

    void onCacheResponse(Response response) throws IOException;
}
