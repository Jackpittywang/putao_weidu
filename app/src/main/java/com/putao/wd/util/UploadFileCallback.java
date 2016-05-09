package com.putao.wd.util;

/**
 * Created by Administrator on 2016/5/9.
 */
public abstract class UploadFileCallback {
    protected abstract void fileUploadSuccess(String filePath);

    void fileUploadFinish() {

    }
    void fileUploadFail(String filePath) {

    }

}