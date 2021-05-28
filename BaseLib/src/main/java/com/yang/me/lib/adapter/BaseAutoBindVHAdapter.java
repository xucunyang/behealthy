package com.yang.me.lib.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yang.me.lib.bean.BaseBean;
import com.yang.me.lib.vh.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Description: adapter基类
 *
 * Author: xucunyang
 * Time: 2021/4/13 16:57
 * </pre>
 */
public abstract class BaseAutoBindVHAdapter<VH extends BaseViewHolder<T>, T extends BaseBean> extends RecyclerView.Adapter<VH> {

    private List<T> data = new ArrayList<>();

    private OnItemClickListener<T> mClickListener;

    private OnItemClickListener<T> mOnLongClickListener;

    public BaseAutoBindVHAdapter(List<T> data) {
        this.data.addAll(data);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        final T itemBean = getItemBean(position);
        holder.onBindVH(position, itemBean);

        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v, position, (T) itemBean);
                }
            });
        }

        // long click
        if (mOnLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnLongClickListener.onItemClick(view, position, (T) itemBean);
                    return false;
                }
            });
        }
    }

    private T getItemBean(int position) {
        if (data != null && data.size() >= position + 1) {
            return data.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public OnItemClickListener<T> getClickListener() {
        return mClickListener;
    }

    public void setClickListener(OnItemClickListener<T> listener) {
        this.mClickListener = listener;
    }

    public void setOnLongClickListener(OnItemClickListener<T> mOnLongClickListener) {
        this.mOnLongClickListener = mOnLongClickListener;
    }

    public void updateData(List<T> list) {
        this.data.clear();
        this.data.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

}
