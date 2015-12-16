package com.putao.wd.start.comment;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.start.comment.adapter.EmojiAdapter;
import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.emoji.Emoji;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.util.List;

import butterknife.Bind;

/**
 * 表情框
 * Created by guchenkai on 2015/12/15.
 */
public class EmojiFragment extends BasicFragment {
    public static final String EVENT_CLICK_EMOJI = "click_emoji";
    public static final String EVENT_DELETE_EMOJI = "delete_emoji";

    @Bind(R.id.rv_emojis)
    BasicRecyclerView rv_emojis;

    private EmojiAdapter adapter;
    private List<Emoji> emojis;
    private int mDeleteDrawable;

    public EmojiFragment(List<Emoji> emojis, int deleteDrawable) {
        this.emojis = emojis;
        mDeleteDrawable = deleteDrawable;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_emoji;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        adapter = new EmojiAdapter(mActivity, emojis, mDeleteDrawable);
        rv_emojis.setAdapter(adapter);
        rv_emojis.setOnItemClickListener(new OnItemClickListener<Emoji>() {
            @Override
            public void onItemClick(Emoji emoji, int position) {
                if (StringUtils.equals(emoji.getName(), "end")) {
                    EventBusHelper.post(emoji, EVENT_DELETE_EMOJI);
                    return;
                }
                EventBusHelper.post(emoji, EVENT_CLICK_EMOJI);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
