package com.putao.wd.me.message.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.MessageNotifyItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wango on 2015/12/2.
 */
public class MsgReplyAdapter  extends BasicAdapter<String,MsgReplyAdapter.MsgReplyViewHolder> {

    public MsgReplyAdapter(Context context, List<String> messagereplyitems) {
        super(context, messagereplyitems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_msgreply_item;
    }

    @Override
    public MsgReplyViewHolder getViewHolder(View itemView, int viewType) {
        return new MsgReplyViewHolder(itemView);
    }

    @Override
    public void onBindItem(MsgReplyViewHolder holder, String messagenotifyitem, int position) {
        holder.tv_msgreply_title.setText(messagenotifyitem);
    }

    /**
     *
     */
    static class MsgReplyViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_msgreply_title)
        TextView tv_msgreply_title;
        public MsgReplyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

