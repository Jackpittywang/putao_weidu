package com.putao.wd.pt_companion.manage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.CompanionBlackboard;
import com.putao.wd.model.Create;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/5.
 */
public class BlackboardActivityAdapter extends LoadMoreAdapter<CompanionBlackboard, BlackboardActivityAdapter.BlackboardHolder> {

    private Context mContext;

    public BlackboardActivityAdapter(Context context, List<CompanionBlackboard> list) {
        super(context, list);
        mContext = context;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_blackboard_item;
    }

    @Override
    public BlackboardHolder getViewHolder(View itemView, int viewType) {
        return new BlackboardHolder(itemView);
    }

    @Override
    public void onBindItem(BlackboardHolder holder, CompanionBlackboard blackboard, int position) {

        holder.iv_sign.setImageURL(blackboard.getIcon());// TODO
        holder.tv_title.setText(blackboard.getTitle());
        holder.tv_content.setText(blackboard.getSubtitle());


        holder.ll_time.setVisibility(blackboard.isShowDate() ? View.VISIBLE : View.GONE);
        if (blackboard.isShowDate())
            holder.tv_time.setText(blackboard.getTime());

        switch (blackboard.getType()){
            case 1://文章
                holder.ll_blackboard_time.setVisibility(View.GONE);
                holder.tv_participate.setText("已有" + blackboard.getTags().get(0).getLike_count() + "用户点了赞");
                break;
            case 2://创意
                holder.ll_blackboard_time.setVisibility(View.GONE);
                holder.tv_participate.setText("已有" + blackboard.getTags().get(0).getAttend_count() + "用户参与");
                break;
            case 3://话题
                holder.ll_blackboard_time.setVisibility(View.GONE);
                holder.tv_participate.setText("已有" + blackboard.getTags().get(0).getAttend_count() + "用户参与");
                break;
            case 4://运营
                holder.ll_blackboard_time.setVisibility(View.GONE);
                holder.tv_participate.setText("已有" + blackboard.getTags().get(0).getAttend_count() + "用户参与");
                break;
            case 5://活动
                holder.ll_blackboard_time.setVisibility(View.VISIBLE);
                holder.tv_action_status.setVisibility(View.VISIBLE);
                holder.tv_action_status.setText(blackboard.getTags().get(0).getStatus() == 0 ? "未开始" : (blackboard.getTags().get(0).getStatus() == 1 ? "进行中" : "已结束") );
                holder.tv_time_quantum.setText("活动时间：" + blackboard.getTags().get(0).getStart_time()+"-"+blackboard.getTags().get(0).getEnd_time());
                holder.tv_participate.setText("已有" + blackboard.getTags().get(0).getAttend_count() + "用户参与活动");
                break;
        }
    }

    static class BlackboardHolder extends BasicViewHolder {


        @Bind(R.id.ll_time)
        LinearLayout ll_time;
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