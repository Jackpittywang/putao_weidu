package com.putao.wd.me.message.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.PraiseDetail;
import com.sunnybear.library.view.emoji.EmojiTextView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 消息中心：”赞”适配器
 * Created by wango on 2015/12/3.
 */
public class PraiseAdapter extends LoadMoreAdapter<PraiseDetail, PraiseAdapter.PraiseViewHolder> {

    public PraiseAdapter(Context context, List<PraiseDetail> praiseDetail) {
        super(context, praiseDetail);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_message_praise_item;
    }

    @Override
    public PraiseViewHolder getViewHolder(View itemView, int viewType) {
        return new PraiseViewHolder(itemView);
    }

    @Override
    public void onBindItem(PraiseViewHolder holder, PraiseDetail praiseDetail, int position) {
//        holder.iv_praise_headericon.setImageURL(messagePraiseItem.getHeadIconUrl());
//        holder.tv_praise_usernickname.setText(messagePraiseItem.getPraiseUserNickname());
//        holder.tv_praise_date.setText(messagePraiseItem.getDate());
        Spanned sstr= Html.fromHtml("<font color=#313131>" + praiseDetail.getContent() + "</font>");
        holder.tv_praised_content.setText(sstr);
    }

    /**
     *
     */
    static class PraiseViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_head_icon)
        ImageDraweeView iv_head_icon;//用户头像
        @Bind(R.id.tv_nickname)
        TextView tv_nickname;//用户昵称
        @Bind(R.id.tv_praise_date)
        TextView tv_praise_date;//赞时间
        @Bind(R.id.tv_praised_content)
        EmojiTextView tv_praised_content;//被赞内容

        public PraiseViewHolder(View itemView) {
            super(itemView);
        }
    }
}

