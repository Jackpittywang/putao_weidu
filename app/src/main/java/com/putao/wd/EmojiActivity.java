package com.putao.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by guchenkai on 2015/12/15.
 */
@Deprecated
public class EmojiActivity extends BasicFragmentActivity implements View.OnClickListener {
    @Bind(R.id.vp_emojis)
    ViewPager vp_emojis;

    private Map<String, String> emojiMap;
    private List<Emoji> emojis;

    private boolean isShowEmoji = false;

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_emoji;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        emojiMap = GlobalApplication.getEmojis();
        emojis = new ArrayList<>();
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            emojis.add(new Emoji(entry.getKey(), entry.getValue()));
        }

        vp_emojis.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                int start = position * 20;
                int end = position * 20 + 20 > emojis.size() ? emojis.size() : position * 20 + 20;
                List<Emoji> list = ListUtils.cutOutList(emojis, start, end);
                list.add(new Emoji("end"));
                return new EmojiFragment(list);
            }

            @Override
            public int getCount() {
                return emojis.size() / 20 + 1;
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.tv_emojis)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_emojis:
                isShowEmoji = isShowEmoji ? false : true;
                vp_emojis.setVisibility(isShowEmoji ? View.VISIBLE : View.GONE);
                break;
        }
    }
}
