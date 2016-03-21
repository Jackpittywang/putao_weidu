package com.putao.wd.image;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.PicClickResult;
import com.putao.wd.model.PicList;
import com.sunnybear.library.controller.BasicFragmentActivity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/17.
 */
public class ImageDetailActivity extends BasicFragmentActivity {
    private static final String STATE_INDEX = "state_index";
    public static final String IMAGE_URL = "image_url";
    @Bind(R.id.image_detail_close)
    ImageView image_detail_close;
    @Bind(R.id.image_detail_num)
    TextView image_detail_num;
    @Bind(R.id.image_pager)
    HackyViewPager image_pager;
    PicClickResult picClickResult;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_explore_imagedetail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addListener();
        picClickResult = (PicClickResult) args.getSerializable(IMAGE_URL);
        //开始的张数
        int startNum = picClickResult.getClickIndex();
        ArrayList<PicList> list = picClickResult.getPicList();
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getSupportFragmentManager(), list);
        image_pager.setAdapter(mAdapter);

        CharSequence image_num = getString(R.string.viewpager_indicator, 1, image_pager
                .getAdapter().getCount());
        image_detail_num.setText(image_num);
        // 更新下标
        image_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence image_num = getString(R.string.viewpager_indicator,
                        arg0 + 1, image_pager.getAdapter().getCount());
                image_detail_num.setText(image_num);
            }

        });
        if (saveInstanceState != null) {
            startNum = saveInstanceState.getInt(STATE_INDEX);
        }
        image_pager.setCurrentItem(startNum);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_INDEX, image_pager.getCurrentItem());
    }

    public void addListener() {
        /**
         * 返回
         * */
        image_detail_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<PicList> list;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<PicList> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = list.get(position).getSrc();
            return ImageDetailFragment.newInstance(url);
        }

    }


}
