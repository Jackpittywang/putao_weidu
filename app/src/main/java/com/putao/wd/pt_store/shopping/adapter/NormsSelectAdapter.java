package com.putao.wd.pt_store.shopping.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Norms;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.AmountSelectLayout;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.select.Tag;
import com.sunnybear.library.view.select.TagBar;

import java.util.List;

import butterknife.Bind;

/**
 * 规格选择适配器
 * Created by guchenkai on 2015/11/30.
 */
public class NormsSelectAdapter extends BasicAdapter<Norms, BasicViewHolder> {
    public static final String EVENT_SEL_TAG = "sel_tag";
    public static final String EVENT_DEFAULT_TAG = "default_tag";
    public static final String EVENT_COUNT = "count";


    private int mMaxCount;
    private static final int TYPE_NORMS = 1;
    private static final int TYPE_COUNT = 2;
    private CountSelectViewHolder countViewHolder;
//    private boolean isLast = false;

    public NormsSelectAdapter(Context context, List<Norms> normses) {
        super(context, normses);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return TYPE_COUNT;
        return TYPE_NORMS;
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_NORMS:
                return R.layout.popup_shopping_car_item_norms;
            case TYPE_COUNT:
                return R.layout.popup_shopping_car_item_count;
        }
        return 0;
    }

    public void setMaxCount(int maxCount) {
        mMaxCount = maxCount;
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case TYPE_NORMS:
                return new NormsSelectViewHolder(itemView);
            case TYPE_COUNT:
                return new CountSelectViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindItem(BasicViewHolder holder, Norms norms, int position) {
        if (holder instanceof NormsSelectViewHolder) {
            NormsSelectViewHolder viewHolder = (NormsSelectViewHolder) holder;
            viewHolder.tv_title.setText(norms.getTitle());
            viewHolder.tb_tag.replaceAllTag(norms.getTags(), 0);
            EventBusHelper.post(viewHolder.tb_tag.getTag(0), EVENT_DEFAULT_TAG);//传递默认选中值
            viewHolder.tb_tag.setonTagItemCheckListener(new TagBar.OnTagItemCheckListener() {
                @Override
                public void onTagItemCheck(Tag tag, int position) {
//                    isLast = false;
                    EventBusHelper.post(tag, EVENT_SEL_TAG);
                }
            });
        } else if (holder instanceof CountSelectViewHolder) {
            countViewHolder = (CountSelectViewHolder) holder;
            if (StringUtils.equals("reset", norms.getTitle()))
                countViewHolder.al_count.reset();
            countViewHolder.al_count.setMaxCount(mMaxCount);
            countViewHolder.al_count.setOnAmountSelectedListener(new AmountSelectLayout.OnAmountSelectedListener() {
                @Override
                public void onAmountSelected(int count, boolean isPlus ,boolean isLast) {
                    EventBusHelper.post(count, EVENT_COUNT);

                    if(isPlus)
                        if(isLast)
                            ToastUtils.showToastShort(context, "库存不足");


//                    if(isPlus){
//                        if (isLast)
//                            ToastUtils.showToastShort(context, "库存不足");
//                        if (mMaxCount <= count)
//                            isLast = true;
//                    }else
//                        isLast = false;
                }
            });
        }
    }

    public void resetAmount() {
        Norms norms = new Norms();
        norms.setTitle("reset");
        replace(getItemCount() - 1, norms);
    }

    /**
     * 规格选择
     */
    static class NormsSelectViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tb_tag)
        TagBar tb_tag;

        public NormsSelectViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 数量选择
     */
    static class CountSelectViewHolder extends BasicViewHolder {
        @Bind(R.id.al_count)
        AmountSelectLayout al_count;

        public CountSelectViewHolder(View itemView) {
            super(itemView);
        }
    }
}
