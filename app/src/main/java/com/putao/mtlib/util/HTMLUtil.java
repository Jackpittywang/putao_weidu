package com.putao.mtlib.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JIDONGDONG on 2015/7/16.
 */
public class HTMLUtil {


    public static String setWidth(float width, String html) {
        float videoHeight = (width * 9) / 16 + 2;
        return setImageWidth("<iframe class=\"video_iframe\"([^>]*)", setImageWidth("<img([^>]*)", html, width, videoHeight, false), width, videoHeight, true);
    }

    private static String setImageWidth(String reg, String explanation, float width, float height, boolean isVideo) {
        Pattern p = Pattern.compile(reg);
        String replaceAll = explanation;
        Matcher m = p.matcher(explanation);// 开始编译
        while (m.find()) {
            String group = m.group(1);
            group = addWidHei(group, width, height, isVideo);
            group = addStyle(group, width, height, isVideo);
            replaceAll = replaceAll.replace(m.group(1), group);
            System.out.println(replaceAll);
            if (isVideo) {
                Pattern pVideo = Pattern.compile(" src=\"([^\"]*)");
                Matcher mVideo = pVideo.matcher(group);// 开始编译
                while (mVideo.find()) {
                    String video = replaceHTML("width=([^&]*)", mVideo.group(1), "width=" + width, isVideo);
                    video = replaceHTML("height=([^&]*)", video, "height=" + height, isVideo);
                    replaceAll = replaceAll.replace(mVideo.group(1), video);
                }
            }
        }
        return replaceAll;
    }

    private static String addWidHei(String group, float width, float height, boolean isVideo) {
        group = replaceHTML("width=\"([^\"]*)", group, " width=\"" + width + "\"", isVideo);
        if (isVideo)
            group = replaceHTML("height=\"([^\"]*)", group, " height=\"" + height + "\"", isVideo);
        else
            group = replaceHTML("height=\"([^\"]*)", group, "", isVideo);

        return group;
    }

    private static String replaceHTML(String reg, String group, String replace, boolean isVideo) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(group);
        if (m.find()) {
            group = group.replace(m.group(), replace);
        } else if (!isVideo) {
            group = replace + group;
        }
        return group;
    }

    private static String addStyle(String group, float width, float height, boolean isVideo) {
        Pattern p1 = Pattern.compile("style=\"([^>]*)");
        Matcher m1 = p1.matcher(group);
        if (m1.find()) {
            group = replaceHTML("width:([^;]*)", group, " width=\"" + width + "\"", isVideo);
            group = replaceHTML("height:([^;]*)", group, "", isVideo);
        } else {
            group = " style=\"width:" + width + ";\"" + group;
        }
        return group;
    }
}
