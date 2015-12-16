package com.putao.wd.start.comment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.putao.wd.R;
import com.putao.wd.start.comment.EmojiFragment;
import com.sunnybear.library.util.ListUtils;
import com.sunnybear.library.view.emoji.Emoji;

import java.util.List;

/**
 * 表情集合适配器
 * Created by guchenkai on mPageCount15/12/16.
 */
public class EmojiFragmentAdapter extends FragmentPagerAdapter {
    private List<Emoji> mEmojis;//表情集合
    private int mPageCount;//一页的表情数量

    public EmojiFragmentAdapter(FragmentManager fm, List<Emoji> emojis, int pageCount) {
        super(fm);
        mEmojis = emojis;
        mPageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        int start = position * mPageCount;
        int end = position * mPageCount + mPageCount > mEmojis.size() ? mEmojis.size() : position * mPageCount + mPageCount;
        List<Emoji> list = ListUtils.cutOutList(mEmojis, start, end);
        list.add(new Emoji("end"));
        return new EmojiFragment(list, R.drawable.btn_emoji_del_select);
    }

    @Override
    public int getCount() {
        return mEmojis.size() / mPageCount + 1;
    }
}
