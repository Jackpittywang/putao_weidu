package com.sunnybear.library.model.http;

/**
 * 缓存查询类型
 * Created by guchenkai on 2016/1/22.
 */
public enum CacheType {
    /**
     * 只查询网络数据
     */
    NETWORK,
    /**
     * 只查询本地缓存
     */
    CACHE,
    /**
     * 先查询本地缓存，如果本地没有，再查询网络数据
     */
    CACHE_ELSE_NETWORK,
    /**
     * 先查询网络数据，如果网络没有，再查询本地缓存
     */
    NETWORK_ELSE_CACHE
}
