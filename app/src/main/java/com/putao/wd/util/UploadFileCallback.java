package com.putao.wd.util;

/**
 * Created by Administrator on 2016/5/9.
 */
public abstract class UploadFileCallback {
    protected abstract void onFileUploadSuccess(String filePath);

    void onFileUploadFinish() {

    }
    protected void onFileUploadFail(String filePath) {

    }

}