package com.putao.wd.util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.putao.wd.GlobalApplication;
import com.sunnybear.library.util.FileUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ResourcesUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表情解析服务
 * Created by guchenkai on 2015/12/10.
 */
public class EmojiService extends Service {
    private Map<String, String> emojis = new ConcurrentHashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
//        File file = ResourcesUtils.getAssetsFile("emoji.zip");
//        Logger.d(file.getAbsolutePath());
        try {
            ResourcesUtils.unZip(this, "emoji.zip", "", true);//解压表情包
            Logger.d("表情包解压完成");
            File emoji = new File(GlobalApplication.sdCardPath + File.separator + "emoji", "set.txt");
            String source = FileUtils.readFile(emoji).replace("\uFEFF", "");
            Logger.d(source);
            String[] sources = source.split("\\n");
            for (String s : sources) {
                String[] s1 = s.split(",");
                emojis.put(s1[0], GlobalApplication.sdCardPath + File.separator + "emoji" + File.separator + s1[1]);
            }
            Logger.d(emojis.get("[呲牙]"));
            GlobalApplication.setEmojis(emojis);

            stopSelf();
        } catch (IOException e) {
            Logger.e("表情包解压完成", e);
            stopSelf();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
