package com.sunnybear.library.util;

import java.math.BigDecimal;

/**
 * 数学工具
 * Created by guchenkai on 2015/12/17.
 */
public class MathUtils {

    /**
     * 乘法
     *
     * @param figure      数字
     * @param coefficient 因数
     * @return 乘积
     */
    public static String multiplication(String figure, int coefficient) {
        BigDecimal b1 = new BigDecimal(figure);
        BigDecimal b2 = new BigDecimal(coefficient);
        return b1.multiply(b2).toString();
    }
}
