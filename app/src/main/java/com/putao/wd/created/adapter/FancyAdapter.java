package com.putao.wd.created.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.model.Create;
import com.putao.wd.model.Creates;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;
import java.util.Map;

import butterknife.Bind;


/**
 * Created by zhanghao on 2016/2/28.
 */
public class FancyAdapter extends LoadMoreAdapter<Create, FancyAdapter.FancyHolder> {

    private Context mContext;
    public final static String COOL = "cool";
    public final static String COMMENT = "comment";
    public FancyAdapter(Context context, List<Create> creates) {
        super(context, creates);
        mContext = context;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_fancy_item;
    }

    @Override
    public FancyHolder getViewHolder(View itemView, int viewType) {
        return new FancyHolder(itemView);
    }

    @Override
    public void onBindItem(final FancyHolder holder, final Create create, int position) {
        /*Map map = JSONObject.parseObject(create.getTag());
        if (map != null && map.size() > 0) {
            holder.rl_tag.setVisibility(View.VISIBLE);
            holder.tv_tag1.setText(null == map.get("1") ? "" : map.get("1").toString());
            holder.tv_tag2.setText(null == map.get("2") ? "" : map.get("2").toString());
            holder.tv_tag3.setText(null == map.get("3") ? "" : map.get("3").toString());
            holder.tv_tag4.setText(null == map.get("4") ? "" : map.get("4").toString());
            holder.tv_tag2.setVisibility(null == map.get("2") ? View.GONE : View.VISIBLE);
            holder.tv_tag3.setVisibility(null == map.get("3") ? View.GONE : View.VISIBLE);
            holder.tv_tag4.setVisibility(null == map.get("4") ? View.GONE : View.VISIBLE);
        } else
            holder.rl_tag.setVisibility(View.GONE);*/
        holder.tv_title.setText(create.getTitle());
        holder.tv_content.setText(create.getDescrip());
        holder.iv_icon.setImageURL(create.getAvatar());
        holder.tv_count_comment.setText(create.getComment().getCount() + "");
        holder.tv_count_cool.setText(create.getVote().getUp() + "");
        holder.sb_cool_icon.setClickable(false);
        switch (create.getVote_status()) {
            case 0:
                holder.tv_count_cool.setTextColor(0xff959595);
                holder.sb_cool_icon.setState(false);
                break;
            case 1:
                holder.tv_count_cool.setTextColor(0xff48cfae);
                holder.sb_cool_icon.setState(true);
                break;
            case 2:
                holder.tv_count_cool.setTextColor(0xff959595);
                holder.sb_cool_icon.setState(false);
                break;
        }
        holder.ll_cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (create.getVote_status() != 0) {
                    ToastUtils.showToastShort(mContext, "您已经表过态度了哦");
                    return;
                }
                create.getVote().setUp(create.getVote().getUp() + 1);
                holder.tv_count_cool.setText(create.getVote().getUp() + "");
                holder.tv_count_cool.setTextColor(0xff48cfae);
                holder.sb_cool_icon.setState(true);
                create.setVote_status(1);
                EventBusHelper.post(create.getId(), COOL);
            }
        });
        holder.ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusHelper.post(create.getId(), COMMENT);
            }
        });
    }

    static class FancyHolder extends BasicViewHolder {
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_icon;
        @Bind(R.id.rl_tag)
        RelativeLayout rl_tag;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_tag1)
        TextView tv_tag1;
        @Bind(R.id.tv_tag2)
        TextView tv_tag2;
        @Bind(R.id.tv_tag3)
        TextView tv_tag3;
        @Bind(R.id.tv_tag4)
        TextView tv_tag4;
        @Bind(R.id.tv_count_comment)
        TextView tv_count_comment;
        @Bind(R.id.tv_count_cool)
        TextView tv_count_cool;
        @Bind(R.id.ll_comment)
        LinearLayout ll_comment;
        @Bind(R.id.ll_cool)
        LinearLayout ll_cool;
        @Bind(R.id.sb_cool_icon)
        SwitchButton sb_cool_icon;

        public FancyHolder(View itemView) {
            super(itemView);
        }
    }
}
