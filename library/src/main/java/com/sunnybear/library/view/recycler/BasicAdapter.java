package com.sunnybear.library.view.recycler;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sunnybear.library.controller.handler.WeakHandler;
import com.sunnybear.library.util.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 重新封装Adapter
 * Created by guchenkai on 2015/11/9.
 */
public abstract class BasicAdapter<Item extends Serializable, VH extends BasicViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context context;
    protected Resources resources;
    protected List<Item> mItems;
    private OnItemClickListener<Item> mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private boolean isProcess = false;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public BasicAdapter(Context context, List<Item> items) {
        this.context = context;
        this.resources = context.getResources();
        this.mItems = items != null ? items : new ArrayList<Item>();
    }

    /**
     * 设置item布局id
     *
     * @param viewType view类型
     * @return item布局id
     */
    public abstract int getLayoutId(int viewType);

    /**
     * 设置ViewHolder
     *
     * @param itemView item布局
     * @param viewType view类型
     * @return ViewHolder
     */
    public abstract VH getViewHolder(View itemView, int viewType);

    /**
     * 向itemView上绑定数据
     *
     * @param holder ViewHolder
     * @param item   item数据
     */
    public abstract void onBindItem(VH holder, Item item, int position);

    @Override
    public final int getItemCount() {
        return mItems.size();
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(getLayoutId(viewType), parent, false);
        return getViewHolder(itemView, viewType);
    }

    @Override
    public final void onBindViewHolder(final VH holder, final int position) {
        final Item item = mItems.get(position);
        holder.itemView.setTag(position);
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            long lastClickTime = 0;//最后一次点击时间
            long time = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastClickTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        time = System.currentTimeMillis();
                        long diff = time - lastClickTime;
                        if (diff < 2000) {
                            Logger.d("点击事件");
                            if (mOnItemClickListener != null && !isProcess)
                                mOnItemClickListener.onItemClick(item, position);
                            if (isProcess) Logger.e("重复点击");
                            isProcess = true;
                        } else if (diff >= 2000) {
                            Logger.d("长按事件");
                            if (mOnItemLongClickListener != null)
                                mOnItemLongClickListener.onItemLongClick(item, position);
                        }
                        lastClickTime = time;
                        //初始化按下状态
                        new WeakHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isProcess = false;
                            }
                        }, 500);
                        break;
                }
                return true;
            }
        });
//        if (mOnItemClickListener != null)
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!WidgetUtils.isFastClick() && !isLongClick)
//                        mOnItemClickListener.onItemClick(item, position);
//                    else
//                        Logger.e("重复点击");
//                }
//            });
//        if (mOnItemLongClickListener != null)
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    mOnItemLongClickListener.onItemLongClick(item, position);
//                    return true;
//                }
//            });
        onBindItem(holder, item, position);
    }

    public void add(Item item) {
        int index = mItems.size();
        mItems.add(item);
        notifyItemInserted(index);
    }

    public void addAll(List<Item> items) {
        int indexStart = mItems.size();
        mItems.addAll(items);
        notifyItemRangeInserted(indexStart, mItems.size() - 1);
    }

    public void replace(Item oldItem, Item newItem) {
        int index = mItems.indexOf(oldItem);
        mItems.set(index, newItem);
        notifyItemChanged(index);
    }

    public void replace(int index, Item item) {
        mItems.set(index, item);
        notifyDataSetChanged();
    }

    public void replaceAll(List<Item> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void delete(Item item) {
        mItems.remove(item);
        notifyDataSetChanged();
    }

    public void delete(int index) {
        mItems.remove(index);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public boolean contains(Item item) {
        return mItems.contains(item);
    }
}
