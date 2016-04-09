package com.putao.wd.pt_me.message.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Remind;
import com.putao.wd.model.RemindDetail;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 消息中心：“通知”适配器
 * Created by wango on 2015/12/1.
 */
public class RemindAdapter extends LoadMoreAdapter<Remind, RemindAdapter.RemindViewHolder> {

//    public final  static  String EVENT_REMIND = "event_remind";
//    public RemindAdapter(Context context, List<RemindDetail> remindnotifyitems) {
//        super(context, remindnotifyitems);
//    }
//
//    @Override
//    public int getLayoutId(int viewType) {
//        return R.layout.fragment_message_notify_item;
//    }
//
//    @Override
//    public RemindViewHolder getViewHolder(View itemView, int viewType) {
//        return new RemindViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindItem(RemindViewHolder holder, final RemindDetail remindDetail, int position) {
//        holder.tv_notify_content.setText(remindDetail.getContent());
//        holder.tv_release_time.setText(DateUtils.timeCalculate(Integer.parseInt(remindDetail.getCreate_time())));
//        if (StringUtils.isEmpty(remindDetail.getImg_url()))
//            holder.iv_action_icon.setVisibility(View.GONE);
//        else {
//            holder.iv_action_icon.setVisibility(View.VISIBLE);
//            holder.iv_action_icon.setImageURL(remindDetail.getImg_url());
//        }
//        if (0 == remindDetail.getLocation())
//            holder.tv_check_detail.setVisibility(View.GONE);
//        else if (1 == remindDetail.getLocation()) {
//            holder.tv_check_detail.setVisibility(View.VISIBLE);
//            holder.tv_check_detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    EventBusHelper.post(remindDetail,EVENT_REMIND);
//                }
//            });
//        } else if (2 == remindDetail.getLocation()) {
//            holder.tv_check_detail.setVisibility(View.VISIBLE);
//            holder.tv_check_detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }
//    }

    public final  static  String EVENT_REMIND = "event_remind";
    public RemindAdapter(Context context, List<Remind> remindnotifyitems) {
        super(context, remindnotifyitems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_message_notify_item;
    }

    @Override
    public RemindViewHolder getViewHolder(View itemView, int viewType) {
        return new RemindViewHolder(itemView);
    }

    @Override
    public void onBindItem(RemindViewHolder holder, final Remind remindDetail, int position) {
        holder.tv_notify_content.setText(remindDetail.getNick_name()+"/r/n"+remindDetail.getQuestion());
        holder.tv_release_time.setText(DateUtils.timeCalculate(Integer.parseInt(remindDetail.getRelease_time())));
        if (StringUtils.isEmpty(remindDetail.getHead_img()))
            holder.iv_action_icon.setVisibility(View.GONE);
        else {
            holder.iv_action_icon.setVisibility(View.VISIBLE);
            holder.iv_action_icon.setImageURL(remindDetail.getHead_img());
        }


        holder.tv_check_detail.setText(remindDetail.getAnswer());

//        if (0 == remindDetail.getLocation())
//            holder.tv_check_detail.setVisibility(View.GONE);
//        else if (1 == remindDetail.getLocation()) {
//            holder.tv_check_detail.setVisibility(View.VISIBLE);
//            holder.tv_check_detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    EventBusHelper.post(remindDetail,EVENT_REMIND);
//                }
//            });
//        } else if (2 == remindDetail.getLocation()) {
//            holder.tv_check_detail.setVisibility(View.VISIBLE);
//            holder.tv_check_detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }
    }

    /**
     *
     */
    static class RemindViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_release_time)
        TextView tv_release_time;//通知时间
        @Bind(R.id.tv_notify_content)
        TextView tv_notify_content;//通知内容
        @Bind(R.id.iv_action_icon)
        ImageDraweeView iv_action_icon;//活动封面
        @Bind(R.id.tv_check_detail)
        TextView tv_check_detail;//查看详情

        public RemindViewHolder(View itemView) {
            super(itemView);
        }
    }
}

