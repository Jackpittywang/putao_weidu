package com.sunnybear.library.view.recycler;

import java.io.Serializable;

/**
 * item点击事件
 * Created by guchenkai on 2015/11/9.
 */
public interface OnItemClickListener<Item extends Serializable, VH extends BasicViewHolder> {

    void onItemClick(Item item, VH holder, int position);
}
