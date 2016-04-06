package com.putao.wd;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/6.
 */
public class PhotoAlbumActivity extends BasicFragmentActivity<GlobalApplication> {

    @Bind(R.id.rcPhotoList)
    BasicRecyclerView rcPhotoList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_album;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        rcPhotoList.setLayoutManager(manager);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
