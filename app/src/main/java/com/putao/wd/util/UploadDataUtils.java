package com.putao.wd.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanguoqiang on 16/5/6.
 */
public class UploadDataUtils {

    private static List<Object> uploadDataList = new ArrayList<Object>();
    private static boolean isUploading = false;

    public void addUploadData(Object obj){
        uploadDataList.add(obj);
        if(isUploading == true) return;
        // 如果未启动上传对列，这里启动
        startUploadThread();

    }

    /**
     * 开始队列上传数据
     */
    private static void startUploadThread(){
        isUploading = true;

        Thread uploadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (uploadDataList.size()>0){
                    Object object = uploadDataList.get(0);

                    // 此处开始上传工作
                    //
                    //

                    // 上传结束处理
                    // 通知需要处理的页面

                    //移除数据
                    uploadDataList.remove(0);
                }
                isUploading = false;
            }
        });
        uploadThread.start();
    }
}
