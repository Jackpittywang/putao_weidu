package com.putao.wd.pt_me.message.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.model.Praise;
import com.putao.wd.model.PraiseDetail;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.StringUtils;
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
public class PraiseAdapter extends LoadMoreAdapter<Praise, PraiseAdapter.PraiseViewHolder> {
//
//    public PraiseAdapter(Context context, List<PraiseDetail> praiseDetail) {
//        super(context, praiseDetail);
//    }
//
//    @Override
//    public int getLayoutId(int viewType) {
//        return R.layout.fragment_message_praise_item;
//    }
//
//    @Override
//    public PraiseViewHolder getViewHolder(View itemView, int viewType) {
//        return new PraiseViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindItem(PraiseViewHolder holder, PraiseDetail praiseDetail, int position) {
//        if (!StringUtils.isEmpty(praiseDetail.getHead_img()))
//            holder.iv_head_icon.setImageURL(praiseDetail.getHead_img());
//        else {
//            holder.iv_head_icon.setImageURL(Uri.parse("res://putao/" + R.drawable.img_head_default).toString());
//        }
//        holder.tv_nickname.setText(praiseDetail.getNick_name());
//        holder.tv_praise_date.setText(DateUtils.timeCalculate(Integer.parseInt(praiseDetail.getCreate_time())));
//        //holder.tv_praised_content.setText(praiseDetail.getContent());
////        Spanned sstr= Html.fromHtml("<font color=#313131>" + praiseDetail.getContent() + "</font>");
//        holder.tv_praised_content.setText(AccountHelper.getCurrentUserInfo().getNick_name() + ":" + praiseDetail.getContent());
//    }


    public PraiseAdapter(Context context, List<Praise> praiseDetail) {
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
    public void onBindItem(PraiseViewHolder holder, Praise praiseDetail, int position) {
        if (!StringUtils.isEmpty(praiseDetail.getHead_img()))
            holder.iv_head_icon.setImageURL(praiseDetail.getHead_img());
        else {
            holder.iv_head_icon.setImageURL(Uri.parse("res://putao/" + R.drawable.img_head_default).toString());
        }
        holder.tv_nickname.setText(praiseDetail.getNick_name());
        holder.tv_praise_date.setText(DateUtils.timeCalculate(Integer.parseInt(praiseDetail.getRelease_time())));
        //holder.tv_praised_content.setText(praiseDetail.getContent());
//        Spanned sstr= Html.fromHtml("<font color=#313131>" + praiseDetail.getContent() + "</font>");


        if(praiseDetail.getParent_content().getComment_id() == 1){
            holder.tv_praised_content.setText(   AccountHelper.getCurrentUserInfo().getNick_name() + ":" + praiseDetail.getParent_content().getContent());
        }else if(praiseDetail.getParent_content().getOpus_id() == 3){
            holder.tv_praised_content.setText(   AccountHelper.getCurrentUserInfo().getNick_name() + ":" +praiseDetail.getParent_content().getContent());
        }else{
            holder.tv_praised_content.setText(   AccountHelper.getCurrentUserInfo().getNick_name() + ":" + praiseDetail.getParent_content().getContent());
        }
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

