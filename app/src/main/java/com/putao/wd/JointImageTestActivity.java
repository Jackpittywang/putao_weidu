package com.putao.wd;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.Bind;

/**
 * 截屏测试
 * Created by guchenkai on 2015/12/11.
 */
@Deprecated
public class JointImageTestActivity extends PTWDActivity {
    @Bind(R.id.root)
    ScrollView root;

    private int mSrollViewHeight = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_joint_image_test;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onRightAction() {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                child.setBackgroundResource(R.color.white);
                mSrollViewHeight += child.getHeight();
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(root.getWidth(), mSrollViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        root.draw(canvas);
        File file = new File(GlobalApplication.sdCardPath + File.separator + "screenshot.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Logger.d(isSuccess ? "保存图片成功" : "保存图片成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
