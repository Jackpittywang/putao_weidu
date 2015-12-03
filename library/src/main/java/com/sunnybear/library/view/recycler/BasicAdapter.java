package com.sunnybear.library.view.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    protected List<Item> mItems;
    private OnItemClickListener<Item> mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private boolean isProcess = false;
    private View mItemView;

    private int mProcessDrawable;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setProcessDrawable(int processDrawable) {
        mProcessDrawable = processDrawable;
    }

    public BasicAdapter(Context context, List<Item> items) {
        this.context = context;
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
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * 获得item
     *
     * @param position 标号
     * @return item
     */
    public Item getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(context).inflate(getLayoutId(viewType), parent, false);
        mItemView.setBackgroundResource(mProcessDrawable);
        return getViewHolder(mItemView, viewType);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        final Item item = mItems.get(position);
        final View itemView = holder.itemView;
        itemView.setTag(position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isProcess) Logger.e("重复点击");
                if (mOnItemClickListener != null && !isProcess)
                    mOnItemClickListener.onItemClick(item, position);
                isProcess = true;
                new WeakHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isProcess = false;
                    }
                }, 200);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Logger.d("长按事件");
                if (mOnItemLongClickListener != null)
                    mOnItemLongClickListener.onItemLongClick(item, position);
                return true;
            }
        });
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
        replace(index, newItem);
    }

    public void replace(int index, Item item) {
        mItems.set(index, item);
        notifyItemChanged(index);
    }

    public void replaceAll(List<Item> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void delete(Item item) {
        int index = mItems.indexOf(item);
        delete(index);
    }

    public void delete(int index) {
        mItems.remove(index);
        notifyItemRemoved(index);
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public boolean contains(Item item) {
        return mItems.contains(item);
    }
}
