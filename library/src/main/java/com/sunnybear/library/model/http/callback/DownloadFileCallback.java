package com.sunnybear.library.model.http.callback;

/**
 * 文件下载回调
 * Created by guchenkai on 2015/12/18.
 */
public abstract class DownloadFileCallback {

    public void onStart() {
    }

    public abstract void onProgress(int progress, long networkSpeed);

    public abstract void onFinish(boolean isSuccess);
}
