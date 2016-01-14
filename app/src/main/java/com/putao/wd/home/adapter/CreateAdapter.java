package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.explore.adapter.MarketingAdapter;
import com.putao.wd.model.Marketing;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.view.image.RatioImageView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/13.
 */
public class CreateAdapter extends LoadMoreAdapter<Marketing, CreateAdapter.CreateHolder> {

    public CreateAdapter(Context context, List<Marketing> marketings) {
        super(context, marketings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_created_item;
    }

    @Override
    public CreateHolder getViewHolder(View itemView, int viewType) {
        return new CreateHolder(itemView);
    }

    @Override
    public void onBindItem(CreateHolder holder, Marketing marketing, int position) {
        holder.iv_sign.setBackgroundResource(R.drawable.img_p_cover);
    }

    static class CreateHolder extends BasicViewHolder {
        @Bind(R.id.iv_sign)
        RatioImageView iv_sign;

        public CreateHolder(View itemView) {
            super(itemView);
        }
    }
}
