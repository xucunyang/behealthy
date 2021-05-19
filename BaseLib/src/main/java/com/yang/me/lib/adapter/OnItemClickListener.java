package com.yang.me.lib.adapter;

import android.view.View;

public interface OnItemClickListener<T> {

    /**
     * @param view     view
     * @param position index
     * @param bean     数据
     */
    void onItemClick(View view, int position, T bean);

}
