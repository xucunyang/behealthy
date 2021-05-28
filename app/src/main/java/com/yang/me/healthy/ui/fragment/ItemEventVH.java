package com.yang.me.healthy.ui.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.yang.me.healthy.R;
import com.yang.me.healthy.data.bean.TypedEvent;
import com.yang.me.healthy.databinding.ItemEventAddBinding;
import com.yang.me.lib.adapter.OnItemClickListener;
import com.yang.me.lib.vh.BaseAutoBindVH;

public class ItemEventVH extends BaseAutoBindVH<ItemEventAddBinding, TypedEvent> {
    public ItemEventVH(ViewGroup parent) {
        super(R.layout.item_event_add, parent);
    }

    @Override
    public void onBindVH(int position, TypedEvent bean) {
        boolean showSuccess = bean.getTotalProgress() >= bean.getTargetProgress();
        mViewBinding.success.setVisibility(showSuccess ? View.VISIBLE : View.GONE);
        mViewBinding.title.setText(bean.getEventName());
        setText(mViewBinding.title, bean.getEventName());
        setText(mViewBinding.current, String.valueOf(bean.getTotalProgress()));
        setText(mViewBinding.target, String.valueOf(bean.getTargetProgress()));
        setText(mViewBinding.unit, "(" + bean.getUnit() + ")");

        mViewBinding.itemDelete.setVisibility(bean.getShowDelete() ? View.VISIBLE : View.GONE);
        if (mOnDeleteListener != null) {
            mViewBinding.itemDelete.setOnClickListener(v -> {
                        mOnDeleteListener.onItemClick(v, position, bean);
                    }
            );
        }
    }

    private OnItemClickListener<TypedEvent> mOnDeleteListener;

    public void setOnDeleteListener(OnItemClickListener<TypedEvent> mOnDeleteListener) {
        this.mOnDeleteListener = mOnDeleteListener;
    }
}
