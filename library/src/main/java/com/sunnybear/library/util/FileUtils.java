package com.sunnybear.library.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

/**
 * 文件操作工具
 * Created by guchenkai on 2015/12/10.
 */
public class FileUtils {

    /**
     * 读取文件内容
     *
     * @param file    文件
     * @param charset 文件编码
     * @return 文件内容
     */
    public static String readFile(File file, String charset) {
        String fileContent = "";
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), charset);
            BufferedReader reader = new BufferedReader(read);
            String line = "";
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (i == 0)
                    fileContent = line;
                else
                    fileContent = fileContent + "\n" + line;
                i++;
            }
            read.close();
        } catch (Exception e) {
            Logger.e("读取文件内容操作出错", e);
        }
        return fileContent;
    }

    /**
     * 读取文件内容
     *
     * @param file 文件
     * @return 文件内容
     */
    public static String readFile(File file) {
        return readFile(file, "UTF-8");
    }

    /**
     * 获取文件的SHA1值
     *
     * @param file 目标文件
     * @return 文件的SHA1值
     */
    public static String getSHA1ByFile(File file) {
        if (!file.exists()) return "文件不存在";
        long time = System.currentTimeMillis();
        InputStream in = null;
        String value = null;
        try {
            in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            int numRead = 0;
            while (numRead != -1) {
                numRead = in.read(buffer);
                if (numRead > 0) digest.update(buffer, 0, numRead);
            }
            byte[] sha1Bytes = digest.digest();
            String t = new String(buffer);
            value = convertHashToString(sha1Bytes);
        } catch (Exception e) {
            Logger.e(e);
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    Logger.e(e);
                }
        }
        return value;
    }

    /**
     * @param hashBytes
     * @return
     */
    private static String convertHashToString(byte[] hashBytes) {
        String returnVal = "";
        for (int i = 0; i < hashBytes.length; i++) {
            returnVal += Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return returnVal.toLowerCase();
    }

    /**
     * 获取上传文件的文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        String filename = new File(filePath).getName();
        if (filename.length() > 80) {
            filename = filename.substring(filename.length() - 80, filename.length());
        }
        return filename;
    }
}
