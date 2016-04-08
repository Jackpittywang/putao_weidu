package com.putao.wd.pt_companion.manage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.CompanionCampaign;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/5.
 */
public class CampaignAdapter extends LoadMoreAdapter<CompanionCampaign, CampaignAdapter.BlackboardHolder> {

    private Context mContext;

//    private static final int ONEDAY = 24 * 60 * 60 * 1000;
//
//    private static int MILLISECOND = 1000;
//    private static final int TODAY = 0;
//    private static final int YESTERDAY = 1;
//    private static final int WEEKDAY = 2;
//    private static final int PREVIOUSLYDAY = 3;


//    private long mToday;
//
//    {
//        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
//        Date date = null;
//        try {
//            date = format.parse(format.format(new Date()));
//            mToday = date.getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    public CampaignAdapter(Context context, List<CompanionCampaign> list) {
        super(context, list);
        mContext = context;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_campaign_item;
    }

    @Override
    public BlackboardHolder getViewHolder(View itemView, int viewType) {
        return new BlackboardHolder(itemView);
    }

    @Override
    public void onBindItem(BlackboardHolder holder, CompanionCampaign blackboard, int position) {

        holder.iv_sign.setImageURL(blackboard.getIcon());
        holder.tv_title.setText(blackboard.getTitle());
        holder.tv_content.setText(blackboard.getSubtitle());


        holder.tv_time.setVisibility(blackboard.isShowDate() ? View.VISIBLE : View.GONE);
        if (blackboard.isShowDate()) {

            try {
                holder.tv_time.setText("───   " + DateUtils.getSecondsToDate(Integer.parseInt(blackboard.getTime())) + "   ───");
            } catch (ParseException e) {
                e.printStackTrace();
            }
//
//            long time = Integer.valueOf(blackboard.getTime()) * MILLISECOND;
//
//
//            String format = "";
//            switch (getDateState(time)) {
//                case TODAY:
//                    format = DateUtils.secondToDate(Integer.parseInt(blackboard.getTime()), "H:dd");
//
//                    break;
//                case YESTERDAY:
//                    format = "昨天" + DateUtils.secondToDate(Integer.parseInt(blackboard.getTime()), "H:dd");
//
//                    break;
//                case WEEKDAY:
//                    format = DateUtils.secondToDate(Integer.parseInt(blackboard.getTime()), "E H:dd");
//                    break;
//                case PREVIOUSLYDAY:
//                    format = DateUtils.secondToDate(Integer.parseInt(blackboard.getTime()), "yyyy年M月d日 H:dd");
//                    break;
//            }
//            holder.tv_time.setText("─── " + format + " ───");
        }

        switch (blackboard.getId()) {
            case 1://文章
                holder.ll_blackboard_time.setVisibility(View.GONE);
                holder.tv_participate.setText("已有" + blackboard.getTags().getUser_num() + "用户点了赞");
                break;
            case 2://创意
                holder.ll_blackboard_time.setVisibility(View.GONE);
                holder.tv_participate.setText("已有" + blackboard.getTags().getUser_num() + "用户参与");
                break;
            case 3://话题
                holder.ll_blackboard_time.setVisibility(View.GONE);
                holder.tv_participate.setText("已有" + blackboard.getTags().getUser_num() + "用户参与");
                break;
            case 4://运营
                holder.ll_blackboard_time.setVisibility(View.GONE);
                holder.tv_participate.setText("已有" + blackboard.getTags().getUser_num() + "用户参与");
                break;
            case 5://活动
                holder.ll_blackboard_time.setVisibility(View.VISIBLE);
                holder.tv_action_status.setVisibility(View.VISIBLE);
//                holder.tv_action_status.setText(blackboard.getTags().getStatus() == 0 ? "未开始" : (blackboard.getTags().getStatus() == 1 ? "进行中" : "已结束"));
                holder.tv_action_status.setText(blackboard.getTags().getPostfix());
                holder.tv_time_quantum.setText("活动时间：" + DateUtils.secondToDate(Integer.parseInt(blackboard.getTags().getStart_time()),"yyyy年M月d日") + "至" + DateUtils.secondToDate(Integer.parseInt(blackboard.getTags().getEnd_time()), "M月d日"));
                holder.tv_participate.setText("已有" + blackboard.getTags().getUser_num() + "用户参与活动");
                break;
        }//2016年3月12日至4月2日
    }

//    /**
//     * 设置标记为 控制时间显示的格式
//     *
//     * @param time
//     * @return
//     */
//    private int getDateState(long time) {
//        if (time >= mToday) {
//            return TODAY;
//        } else if ((time + 1 * ONEDAY) >= mToday) {
//            return YESTERDAY;
//        } else if ((time + 3 * ONEDAY) > mToday) {
//            return WEEKDAY;
//        } else {
//            return PREVIOUSLYDAY;
//        }
//    }

    static class BlackboardHolder extends BasicViewHolder {


        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.iv_sign)
        ImageDraweeView iv_sign;
        @Bind(R.id.tv_action_status)
        TextView tv_action_status;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_tag)
        TextView tv_tag;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.ll_blackboard_time)
        LinearLayout ll_blackboard_time;
        @Bind(R.id.tv_time_quantum)
        TextView tv_time_quantum;
        @Bind(R.id.tv_participate)
        TextView tv_participate;

        public BlackboardHolder(View itemView) {
            super(itemView);
        }
    }
}
