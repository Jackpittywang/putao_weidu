package com.sunnybear.library.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间工具类
 * Created by guchenkai on 2015/11/26.
 */
public final class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();
    private static final String[] weeks = new String[]{};
    //            ResourcesUtils.getStringArray(BasicApplication.getInstance(), R.array.weeks);
    private static final SimpleDateFormat mDataFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat mTimeFormat = new SimpleDateFormat("yyyy-MM-dd mm:HH:ss");

    /**
     * 计算两个日期相差的天数，是否取绝对值
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @param isAbs 是否去绝对值
     * @return 相差天数
     */
    public static int getDaysUnAbs(String date1, String date2, boolean isAbs) {
        int day = 0;
        if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2))
            return 0;
        try {
            Date date = mDataFormat.parse(date1);
            Date myDate = mDataFormat.parse(date2);
            day = (int) ((date.getTime() - myDate.getTime()) / (24 * 60 * 60 * 1000));
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.e(TAG, e);
        }
        return isAbs ? Math.abs(day) : day;
    }

    /**
     * 计算两个日期相差的天数，是否取绝对值
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return 相差天数
     */
    public static int getDaysUnAbs(String date1, String date2) {
        return getDaysUnAbs(date1, date2, true);
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期
     * @return 长时间格式日期
     */
    public static Date strToDateLong(String date) {
        ParsePosition pos = new ParsePosition(0);
        return mTimeFormat.parse(date, pos);
    }

    /**
     * 获取当前的日期时间
     *
     * @return 当前的日期时间
     */
    public static String getCurrentTime() {
        return mTimeFormat.format(new Date());
    }

    /**
     * 获取当前的日期
     *
     * @return 当前的日期
     */
    public static String getCurrentDate() {
        return mDataFormat.format(new Date());
    }

    /**
     * 获取输入日期是星期几
     *
     * @param date 日期
     * @return 星期几
     */
    public static String getWeekDate(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
        return weeks[calendar.get(calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取输入日期是星期几
     *
     * @param date 日期
     * @return 星期几
     */
    public static String getWeekDate(String date) {
        try {
            Date d = mDataFormat.parse(date);
            return getWeekDate(d);
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.e(TAG, e);
        }
        return "星期数未知";
    }

    /**
     * 毫秒转日期
     *
     * @param millisecond 毫秒数
     * @param pattern     正则式
     * @return 日期
     */
    public static String millisecondToDate(long millisecond, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(millisecond);
    }

    /**
     * 毫秒转日期
     *
     * @param millisecond 毫秒数
     * @return 日期
     */
    public static String millisecondToDate(long millisecond) {
        return millisecondToDate(millisecond, "yyyy-MM-dd");
    }


    /**
     * 时间计算
     *
     * @param millisecond 毫秒数
     * @return 计算后显示的文字
     */
    public static String timeCalculate(long millisecond) {
        String result = "";
        long diff = System.currentTimeMillis() - millisecond;//时间差
        if (diff < 1000 * 60)
            return "刚刚以前";
        else if (diff >= 1000 * 60 && diff < 1000 * 60 * 60)
            return diff / (1000 * 60) + "分钟以前";
        else if (diff >= 1000 * 60 * 60 && diff < 1000 * 60 * 60 * 24)
            return diff / (1000 * 60 * 60) + "小时以前";
        else if (diff >= 1000 * 60 * 60 * 24)
            return diff / (1000 * 60 * 60 * 24) + "天以前";
        return result;
    }

    /**
     * 时间计算
     *
     * @param second 秒数
     * @return 计算后显示的文字
     */
    public static String timeCalculate(int second) {
        String result = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentSecond = calendar.get(calendar.SECOND);
        int diff = currentSecond - second;
        if (diff < 60)
            return "刚刚以前";
        else if (diff >= 60 && diff < 60 * 60)
            return diff / 60 + "分钟以前";
        else if (diff >= 60 * 60 && diff < 60 * 60 * 24)
            return diff / (60 * 60) + "小时以前";
        else if (diff >= 60 * 60 * 24)
            return diff / (60 * 60 * 24) + "天以前";
        return result;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("2015-12-3 19:03:00");
        System.out.println(timeCalculate((int) (date.getTime() / 1000)));
    }
}