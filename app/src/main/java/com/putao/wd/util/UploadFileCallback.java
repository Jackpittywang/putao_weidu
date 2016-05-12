package com.putao.wd.util;

import java.io.File;

/**
 * Created by Administrator on 2016/5/9.
 */
public abstract class UploadFileCallback {
    protected abstract void onFileUploadSuccess(String ext, String filename, String hash,String filePath);

    protected void onFileUploadFail(String filePath) {

    }

}