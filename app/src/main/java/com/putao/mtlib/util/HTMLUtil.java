package com.putao.mtlib.util;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.putao.wd.model.PicList;
import com.sunnybear.library.util.Logger;

/**
 * Created by JIDONGDONG on 2015/7/16.
 */
public class HTMLUtil {

    private static int imageCount;
    private static ArrayList<PicList> mPicLists;

    public static String setWidth(float width, String html) {
        mPicLists = new ArrayList<>();
        imageCount = 0;
        float videoHeight = (width * 9) / 16 + 2;
        return setImageWidth("<iframe([^>]*)", setImageWidth("<img([^>]*)", html, width, videoHeight, false), width, videoHeight, true) + SCRIPT_START + JSON.toJSONString(mPicLists) + SCRIPT_END;
    }

    private static String setImageWidth(String reg, String explanation, float width, float height, boolean isVideo) {
        Pattern p = Pattern.compile(reg);
        String replaceAll = explanation;
        Matcher m = p.matcher(explanation);
        while (m.find()) {
            String group = m.group(1);
            group = addWidHei(group, width, height, isVideo);
            if (isVideo) {
                Pattern pVideo = Pattern.compile(" src=\"([^\"]*)");
                Matcher mVideo = pVideo.matcher(group);
                if (mVideo.find()) {
                    String video = replaceHTML("width=([^&]*)", mVideo.group(1), "width=" + width, isVideo);
                    video = replaceHTML("height=([^&]*)", video, "height=" + height, isVideo);
                    group.replace(mVideo.group(1), video);
                }
            } else {
                group = addImageClick(group);
                group = addStyle(group, width, height, isVideo);
            }
            replaceAll = replaceAll.replace(m.group(1), group);
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

    private static String addImageClick(String group) {
        group = replaceHTML("<img([^>]*)", group, " onclick=\"imageClick(" + imageCount + ")\"", false);
        Pattern pVideo = Pattern.compile(" src=\"([^\"]*)");
        Matcher mVideo = pVideo.matcher(group);
        if (mVideo.find()) {
            PicList picList = new PicList();
            picList.setSrc(mVideo.group().substring(6));
            mPicLists.add(picList);
        }
        imageCount++;
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


    private static final String SCRIPT_START = "<script>\n" +
            "  function imageClick(index) {\n" +
            "    var json = { clickIndex: index,\n" +
            "   picList: \n";
    private static final String SCRIPT_END =
            "    };\n" +
                    "    document.location = 'putao://viewPic/' + window.JSON.stringify(json);\n" +
                    "  }\n" +
                    "</script>";
}
