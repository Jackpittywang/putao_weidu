package com.putao.wd;

import android.os.Bundle;

import com.sunnybear.library.controller.BasicFragment;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.OnItemClickListener;

import java.util.List;

import butterknife.Bind;

/**
 * Created by guchenkai on 2015/12/15.
 */
@Deprecated
public class EmojiFragment extends BasicFragment {
    public static final String EVENT_CLICK_EMOJI = "click_emoji";
    public static final String EVENT_DELETE_EMOJI = "delete_emoji";

    @Bind(R.id.rv_emojis)
    BasicRecyclerView rv_emojis;

    private EmojiAdapter adapter;
    private List<Emoji> emojis;

    public EmojiFragment(List<Emoji> emojis) {
        this.emojis = emojis;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment_emoji;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        adapter = new EmojiAdapter(mActivity, emojis);
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
