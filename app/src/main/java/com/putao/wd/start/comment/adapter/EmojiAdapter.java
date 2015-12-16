package com.putao.wd.start.comment.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.putao.wd.Emoji;
import com.putao.wd.R;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * Created by guchenkai on 2015/12/15.
 */
@Deprecated
public class EmojiAdapter extends BasicAdapter<Emoji, EmojiAdapter.EmojiViewHolder> {

    public EmojiAdapter(Context context, List<Emoji> emojis) {
        super(context, emojis);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.test_fragment_emoji_item;
    }

    @Override
    public EmojiViewHolder getViewHolder(View itemView, int viewType) {
        return new EmojiViewHolder(itemView);
    }

    @Override
    public void onBindItem(EmojiViewHolder holder, Emoji emoji, int position) {
        if (!StringUtils.equals(emoji.getName(), "end"))
            holder.iv_emoji.setImageBitmap(BitmapFactory.decodeFile(emoji.getPath()));
        else
            holder.iv_emoji.setImageResource(R.drawable.btn_emoji_del_select);
    }

    /**
     *
     */
    static class EmojiViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_emoji)
        ImageView iv_emoji;

        public EmojiViewHolder(View itemView) {
            super(itemView);
        }
    }
}
