package com.putao.wd.me.myactivities.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;

import com.putao.wd.R;
import com.putao.wd.dto.MyActivitiesItem;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;
import java.util.List;

public class MyActivitiesAdapter extends LoadMoreAdapter<MyActivitiesItem, MyActivitiesAdapter.MyActivitiesViewHolder> {
    public MyActivitiesAdapter(Context context, List<MyActivitiesItem> myActivitiesItems) {
        super(context, myActivitiesItems);
    }

    public int getLayoutId(int viewType) {
        return R.layout.layout_myactivities_item;
    }

    public MyActivitiesAdapter.MyActivitiesViewHolder getViewHolder(View itemView, int viewType) {
        return new MyActivitiesAdapter.MyActivitiesViewHolder(itemView);
    }

    public void onBindItem(MyActivitiesAdapter.MyActivitiesViewHolder holder, MyActivitiesItem myActivitiesItem, int position) {
        holder.iv_myactivities_icon.setImageURL(myActivitiesItem.getActionIcon());
        holder.tv_myactivities_title.setText(myActivitiesItem.getTitle());
        holder.tv_myactivities_content.setText(myActivitiesItem.getIntroduction());
        holder.tv_myactivities_status.setText(myActivitiesItem.getStatus());
//        if("体验".equals(myActivitiesItem.getStatus())) {
//            holder.tv_myactivities_title.setTextColor(Color.parseColor("#000000"));
//        }else{
//            holder.tv_myactivities_status.setBackgroundColor(Color.parseColor("#959595"));
//        }
    }

    static class MyActivitiesViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_myactivities_icon)
        ImageDraweeView iv_myactivities_icon;
        @Bind(R.id.tv_myactivities_title)
        TextView tv_myactivities_title;
        @Bind(R.id.tv_myactivities_content)
        TextView tv_myactivities_content;
        @Bind(R.id.tv_myactivities_status)
        TextView tv_myactivities_status;

        public MyActivitiesViewHolder(View itemView) {
            super(itemView);
        }
    }
}
