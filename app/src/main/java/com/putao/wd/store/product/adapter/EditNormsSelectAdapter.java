package com.putao.wd.store.product.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Cart;
import com.putao.wd.model.Norms;
import com.putao.wd.store.product.util.SpecUtils;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.select.Tag;
import com.sunnybear.library.view.select.TagBar;

import java.util.List;

import butterknife.Bind;

/**
 * 编辑产品规格选择适配器
 * Created by wangou on 2015/11/30.
 */
public class EditNormsSelectAdapter extends BasicAdapter<Norms, EditNormsSelectAdapter.NormsSelectViewHolder> {
    public static final String EVENT_SEL_TAG = "sel_tag";
    public static final String EVENT_DEFAULT_TAG = "default_tag";

    private List<String> skus;

    public EditNormsSelectAdapter(Context context, List<Norms> normses, Cart cart) {
        super(context, normses);
        skus = SpecUtils.parseSku(cart.getSku());
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.popup_shopping_car_item_norms;
    }

    @Override
    public NormsSelectViewHolder getViewHolder(View itemView, int viewType) {
        return new NormsSelectViewHolder(itemView);
    }

    @Override
    public void onBindItem(NormsSelectViewHolder viewHolder, Norms norms, int position) {
        viewHolder.tv_title.setText(norms.getTitle());
        List<Tag> tags = norms.getTags();
        viewHolder.tb_tag.addTags(tags, matchTag(skus.get(position), tags));
        EventBusHelper.post(viewHolder.tb_tag.getTag(0), EVENT_DEFAULT_TAG);//传递默认选中值
        viewHolder.tb_tag.setonTagItemCheckListener(new TagBar.OnTagItemCheckListener() {
            @Override
            public void onTagItemCheck(Tag tag, int position) {
                EventBusHelper.post(tag, EVENT_SEL_TAG);
            }
        });
    }

    /**
     * 匹配tag
     *
     * @param text
     * @param tags
     * @return
     */
    private int matchTag(String text, List<Tag> tags) {
        for (Tag tag : tags) {
            if (StringUtils.equals(text, tag.getText()))
                return tags.indexOf(tag);
        }
        return 0;
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
}
