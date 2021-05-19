package com.yang.me.lib;

import android.view.ViewGroup;

import com.yang.me.lib.adapter.BaseAutoBindVH;
import com.yang.me.lib.bean.SingleSelectBean;
import com.yang.me.lib.databinding.ItemNameSelectBinding;

public class SingleSelectVH extends BaseAutoBindVH<ItemNameSelectBinding, SingleSelectBean> {
    public SingleSelectVH(ViewGroup parent) {
        super(R.layout.item_name_select, parent);
    }

    @Override
    public void onBindVH(int i, SingleSelectBean baseBean) {
        super.refreshUi(i, baseBean);

        SingleSelectBean bean = castBean(baseBean);

        setText(mViewBinding.itemName, bean.getItemName());

        mViewBinding.select.setImageResource(bean.isDefaultSelect() ? android.R.color.background_dark : android.R.color.darker_gray);
    }
}
