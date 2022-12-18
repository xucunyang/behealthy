package com.yang.myapplication.event.dispatch;

import android.view.ViewGroup;

import com.yang.me.lib.bean.SingleSelectBean;
import com.yang.me.lib.vh.BaseAutoBindVH;
import com.yang.myapplication.R;
import com.yang.myapplication.databinding.VhMyBinding;

public class MyVH extends BaseAutoBindVH<VhMyBinding, SingleSelectBean> {
    public MyVH(ViewGroup parent) {
        super(R.layout.vh_my, parent);
    }

    @Override
    public void onBindVH(int position, SingleSelectBean bean) {
        mViewBinding.tv.setText(bean.getItemName());
    }
}
