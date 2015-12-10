package com.sunnybear.library.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.sunnybear.library.BasicApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Resources资源文件工具
 * Created by guchenkai on 2015/11/3.
 */
public final class ResourcesUtils {

    /**
     * 读取Assets文本资源内容
     *
     * @param resName 文件名
     * @return
     */
    public static String getAssetsTextFile(Context context, String resName) {
        String protocal = null;
        try {
            InputStreamReader reader = new InputStreamReader(getAssets(context).open(resName));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            String Result = "";
            while ((line = br.readLine()) != null)
                Result += line;
            return Result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return protocal;
    }

    /**
     * 解压assets的zip压缩文件到指定目录
     *
     * @param context   上下文对象
     * @param assetName 压缩文件名
     * @param outputDir 输出目录
     * @param isReWrite 是否覆盖
     * @throws IOException
     */
    public static void unZip(Context context, String assetName, String outputDir, boolean isReWrite) throws IOException {
        String outputDirectory = BasicApplication.sdCardPath + File.separator + outputDir;
        // 创建解压目标目录
        File file = new File(outputDirectory);
        // 如果目标目录不存在，则创建
        if (!file.exists()) file.mkdirs();
        // 打开压缩文件
        InputStream inputStream = getAssets(context).open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        // 读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        // 使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        // 解压时字节计数
        int count = 0;
        // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            // 如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                // 文件需要覆盖或者是文件不存在
                if (isReWrite || !file.exists()) file.mkdir();
            } else {
                // 如果是文件
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                // 文件需要覆盖或者文件不存在，则解压文件
                if (isReWrite || !file.exists()) {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0)
                        fileOutputStream.write(buffer, 0, count);
                    fileOutputStream.close();
                }
            }
            // 定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }


    /**
     * 获取图片资源的BitmapDrawable
     *
     * @param resId
     * @return
     */
    public static BitmapDrawable getBitmapDrawable(Context context, int resId) {
        return (BitmapDrawable) context.getResources().getDrawable(resId);
    }

    /**
     * 根据字符串资源id获得字符串资源
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 根据字符串数组资源id获得字符串数组资源(Array)
     *
     * @param context
     * @param resId
     * @return
     */
    public static String[] getStringArray(Context context, int resId) {
        return context.getResources().getStringArray(resId);
    }

    /**
     * 根据字符串数组资源id获得字符串数组资源(List)
     *
     * @param context
     * @param resId
     * @return
     */
    public static List<String> getStringList(Context context, int resId) {
        List<String> strings = new ArrayList<String>();
        String[] strs = getStringArray(context, resId);
        for (String string : strs) {
            strings.add(string);
        }
        return strings;
    }

    /**
     * 根据图片资源id获取资源图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }

    /**
     * 根据图片资源id获取资源图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap getBitmap(Context context, int resId) {
        Drawable drawable = getDrawable(context, resId);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        return bitmapDrawable.getBitmap();
    }

    /**
     * 获取Asset资源管理器
     *
     * @param context
     * @return
     */
    public static AssetManager getAssets(Context context) {
        return context.getResources().getAssets();
    }
}
