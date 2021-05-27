package com.yang.me.healthy.ui.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.yang.me.healthy.R;
import com.yang.me.healthy.data.bean.TypedEvent;
import com.yang.me.healthy.databinding.ItemEventAddBinding;
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
    }
}
