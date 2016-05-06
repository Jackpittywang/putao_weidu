package com.putao.wd.pt_discovery;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.model.DiscoveryResource;
import com.putao.wd.model.Resou;
import com.putao.wd.pt_companion.DividerDecoration;
import com.putao.wd.pt_discovery.adapter.ResourceAdapter;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.sunnybear.library.view.recycler.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ResourceFragment extends BasicFragment {

    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_discovery)
    LoadMoreRecyclerView rv_discovery;

    private List<Resou> resous;
    private List<String> tags;
    private List<String> drawables;
    private List<String> hotTags;
    private String title;
    private ResourceAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_resource;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

        freshResource();
        mAdapter = new ResourceAdapter(mActivity, resous, drawables, hotTags);
        // Set layout manager
        rv_discovery.setAdapter(mAdapter);
        int orientation = getLayoutManagerOrientation(getResources().getConfiguration().orientation);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, orientation, false);
        rv_discovery.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        rv_discovery.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        rv_discovery.addItemDecoration(new DividerDecoration(mActivity));

        // Add touch listeners
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(rv_discovery, headersDecor);
        touchListener.setOnHeaderClickListener(
                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {
                        Toast.makeText(mActivity, "Header position: " + position + ", id: " + headerId,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void freshResource() {
//        networkRequest(CompanionApi.getCompanyArticleComment("1", "1", "1", "1"), new SimpleFastJsonCallback<DiscoveryResource>(DiscoveryResource.class,loading) {
//            @Override
//            public void onSuccess(String url, DiscoveryResource result) {
//
//            }
//        });


        loadResous();
        loadDrawables();
        loadTags();

    }

    private int getLayoutManagerOrientation(int activityOrientation) {
        if (activityOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            return LinearLayoutManager.VERTICAL;
        } else {
            return LinearLayoutManager.HORIZONTAL;
        }
    }

    private void loadTags() {
        hotTags = new ArrayList<>();
        hotTags.add("http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg");
        hotTags.add("http://weidu.file.putaocdn.com/file/d6bcf5c2c6d6cbc408ed7fca3718f76323c8d677.jpg");
        hotTags.add("http://weidu.file.putaocdn.com/file/4976bada04f9b3c31fb51e0cd6a3237dff026311.jpg");
        hotTags.add("http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg");
        hotTags.add("http://weidu.file.putaocdn.com/file/d6bcf5c2c6d6cbc408ed7fca3718f76323c8d677.jpg");
        hotTags.add("http://weidu.file.putaocdn.com/file/4976bada04f9b3c31fb51e0cd6a3237dff026311.jpg");
    }

    private void loadDrawables() {
        drawables = new ArrayList<>();
        drawables.add("http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg");
        drawables.add("http://weidu.file.putaocdn.com/file/d6bcf5c2c6d6cbc408ed7fca3718f76323c8d677.jpg");
        drawables.add("http://weidu.file.putaocdn.com/file/4976bada04f9b3c31fb51e0cd6a3237dff026311.jpg");
        drawables.add("http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg");
    }

    private void loadResous() {
        resous = new ArrayList<>();
        tags = new ArrayList<>();
        tags.add("one");
        tags.add("two");
        title = "一二三";
        resous.add(0,new Resou(false, false, "", null, ""));
        resous.add(1, new Resou(true, true, "http://weidu.file.putaocdn.com/file/4976bada04f9b3c31fb51e0cd6a3237dff026311.jpg", tags, title));
        title = "5yue5";
        resous.add(2,new Resou(false, false, "http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg", tags, title));

        tags.add("gogo");
        title = "wen";
        resous.add(3,new Resou(true, false, "http://weidu.file.putaocdn.com/file/d6bcf5c2c6d6cbc408ed7fca3718f76323c8d677.jpg", tags, title));
        title = "mang";
        resous.add(4,new Resou(false, false, "http://weidu.file.putaocdn.com/file/f23c5d489aa3d2a96f12a9d1337af3174c38a3a4.jpg", tags, title));
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
