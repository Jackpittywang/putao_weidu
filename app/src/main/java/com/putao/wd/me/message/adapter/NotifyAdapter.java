package com.putao.wd.me.message.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.NotifyDetail;
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
public class NotifyAdapter extends LoadMoreAdapter<NotifyDetail, NotifyAdapter.NotifyViewHolder> {

    public NotifyAdapter(Context context, List<NotifyDetail> messagenotifyitems) {
        super(context, messagenotifyitems);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_message_notify_item;
    }

    @Override
    public NotifyViewHolder getViewHolder(View itemView, int viewType) {
        return new NotifyViewHolder(itemView);
    }

    @Override
    public void onBindItem(NotifyViewHolder holder, NotifyDetail notifyDetail, int position) {
        parseType(holder, notifyDetail.getType());
        holder.tv_notify_content.setText(notifyDetail.getContent());
        holder.tv_notify_date.setText(DateUtils.timeCalculate(Integer.parseInt(notifyDetail.getCreate_time())));
        if (StringUtils.isEmpty(notifyDetail.getImg_url()))
            holder.iv_action_icon.setVisibility(View.GONE);
        else
            holder.iv_action_icon.setImageURL(notifyDetail.getImg_url());
    }

    /**
     * 解析type
     *
     * @param holder
     * @param type
     */
    private void parseType(NotifyViewHolder holder, String type) {
        switch (type) {
            case "1":
                holder.iv_head_icon.setDefaultImage(R.drawable.img_head_cs);
                holder.tv_nickname.setText("葡萄籽");
                break;
            case "2":
                holder.iv_head_icon.setDefaultImage(R.drawable.img_head_q);
                holder.tv_nickname.setText("小Q");
                break;
            case "3":
                holder.iv_head_icon.setDefaultImage(R.drawable.img_head_q);
                holder.tv_nickname.setText("小Q");
                break;
            case "4":
                holder.iv_head_icon.setDefaultImage(R.drawable.img_head_q);
                holder.tv_nickname.setText("小Q");
                break;
            case "5":
                holder.iv_head_icon.setDefaultImage(R.drawable.img_head_cs);
                holder.tv_nickname.setText("葡萄籽");
                break;
            case "6":
                holder.iv_head_icon.setDefaultImage(R.drawable.img_head_cs);
                holder.tv_nickname.setText("葡萄籽");
                break;
            case "7":
                holder.iv_head_icon.setDefaultImage(R.drawable.img_head_cs);
                holder.tv_nickname.setText("葡萄籽");
                break;
            case "8":
                holder.iv_head_icon.setDefaultImage(R.drawable.img_head_cs);
                holder.tv_nickname.setText("葡萄籽");
                break;
            case "9":
                holder.iv_head_icon.setDefaultImage(R.drawable.img_head_cs);
                holder.tv_nickname.setText("葡萄籽");
                break;
        }
    }

    /**
     *
     */
    static class NotifyViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_head_icon)
        ImageDraweeView iv_head_icon;//用户头像
        @Bind(R.id.tv_nickname)
        TextView tv_nickname;//用户昵称
        @Bind(R.id.tv_notify_date)
        TextView tv_notify_date;//通知时间
        @Bind(R.id.tv_notify_content)
        TextView tv_notify_content;//通知内容
        @Bind(R.id.iv_action_icon)
        ImageDraweeView iv_action_icon;//活动封面
        @Bind(R.id.tv_check_detail)
        TextView tv_check_detail;//查看详情

        public NotifyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

