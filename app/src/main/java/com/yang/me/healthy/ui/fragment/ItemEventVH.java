package com.yang.me.healthy.ui.fragment;

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
        mViewBinding.text.setText(bean.getEventName() + ":" + bean.getUnit());
    }
}
