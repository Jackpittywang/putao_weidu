package com.sunnybear.library.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * list工具类
 * Created by guchenkai on 2015/12/15.
 */
public class ListUtils {

    /**
     * 截取list
     *
     * @param source list源
     * @param start  开始标号
     * @param end    结束标号
     * @return 截取后的list
     */
    public static <T extends Serializable> List<T> cutOutList(List<T> source, int start, int end) {
        List<T> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(source.get(i));
        }
        return result;
    }
}
