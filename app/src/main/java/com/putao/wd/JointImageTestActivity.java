package com.putao.wd;

import android.os.Bundle;
import android.widget.ScrollView;

import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.util.ImageUtils;

import java.io.File;

import butterknife.Bind;

/**
 * 截屏测试
 * Created by guchenkai on 2015/12/11.
 */
@Deprecated
public class JointImageTestActivity extends PTWDActivity {
    @Bind(R.id.root)
    ScrollView root;

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
        ImageUtils.cutOutViewToImage(root, GlobalApplication.sdCardPath + File.separator + "screenshot.jpg");
    }
}
