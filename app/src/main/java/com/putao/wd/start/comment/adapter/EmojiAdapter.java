package com.putao.wd.start.comment.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.putao.wd.R;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.emoji.Emoji;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 表情适配器
 * Created by guchenkai on 2015/12/15.
 */
public class EmojiAdapter extends BasicAdapter<Emoji, EmojiAdapter.EmojiViewHolder> {
    private int mDeleteDrawable;

    public EmojiAdapter(Context context, List<Emoji> emojis, int deleteDrawable) {
        super(context, emojis);
        mDeleteDrawable = deleteDrawable;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_emoji_item;
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
            holder.iv_emoji.setImageResource(mDeleteDrawable);
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
