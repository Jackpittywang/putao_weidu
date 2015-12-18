package com.sunnybear.library.util;

import java.math.BigDecimal;

/**
 * 数学工具
 * Created by guchenkai on 2015/12/17.
 */
public final class MathUtils {

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

    /**
     * 比较两个数字大小
     *
     * @param num1 第一个数字
     * @param num2 第二个数字
     * @return 数字大小
     */
    public static boolean compare(String num1, String num2) {
        double number1 = Double.parseDouble(num1);
        double number2 = Double.parseDouble(num2);
        return Math.max(number1, number2) == number1;
    }
}
