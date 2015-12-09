package com.sunnybear.library.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * json工具类
 * Created by guchenkai on 2015/10/27.
 */
public final class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    /**
     * 判断json类型
     *
     * @param json json
     * @return json类型
     */
    public static JsonType getJSONType(String json) {
        if (TextUtils.isEmpty(json))
            return JsonType.JSON_TYPE_ERROR;
        final char[] strChar = json.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];
        switch (firstChar) {
            case '{':
                Logger.d(TAG, "类型是JsonObject");
                return JsonType.JSON_TYPE_OBJECT;
            case '[':
                Logger.d(TAG, "类型是JsonArray");
                return JsonType.JSON_TYPE_ARRAY;
            default:
                Logger.d(TAG, "类型错误");
                return JsonType.JSON_TYPE_ERROR;
        }
    }

    /**
     * json类型枚举
     */
    public enum JsonType {
        JSON_TYPE_OBJECT,//JSONObject
        JSON_TYPE_ARRAY,//JSONArray
        JSON_TYPE_ERROR//不是JSON格式的字符串
    }

    /**
     * 格式化json
     *
     * @param json json
     * @return 格式化后的json
     */
    public static String jsonFormatter(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        String formatJson = gson.toJson(je);
        return formatJson;
    }
}
