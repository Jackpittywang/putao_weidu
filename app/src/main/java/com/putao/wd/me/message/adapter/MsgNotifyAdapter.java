package com.putao.wd.me.message.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.dto.MessageNotifyItem;
import com.putao.wd.dto.ProductItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 消息通知适配器
 * Created by wango on 2015/12/1.
 *
 */
public class MsgNotifyAdapter extends BasicAdapter<MessageNotifyItem,MsgNotifyAdapter.MsgNotifyViewHolder> {

    public MsgNotifyAdapter(Context context, List<MessageNotifyItem> messagenotifyitems) {
        super(context, messagenotifyitems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_msgnotify_item;
    }

    @Override
    public MsgNotifyViewHolder getViewHolder(View itemView, int viewType) {
        return new MsgNotifyViewHolder(itemView);
    }

    @Override
    public void onBindItem(MsgNotifyViewHolder holder, MessageNotifyItem messagenotifyitem, int position) {
        holder.iv_notify_icon.setImageURL(messagenotifyitem.getIconUrl());
        holder.tv_notify_title.setText(messagenotifyitem.getTitle());
        holder.tv_notify_date.setText(messagenotifyitem.getDate());
        holder.tv_notify_content.setText(messagenotifyitem.getIntro());
    }

    /**
     *
     */
    static class MsgNotifyViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_notify_icon)
        ImageDraweeView iv_notify_icon;
        @Bind(R.id.tv_notify_title)
        TextView tv_notify_title;
        @Bind(R.id.tv_notify_date)
        TextView tv_notify_date;
        @Bind(R.id.tv_notify_content)
        TextView tv_notify_content;

        public MsgNotifyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
