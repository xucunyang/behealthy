package com.yang.me.lib;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yang.me.lib.adapter.BaseAutoBindVH;
import com.yang.me.lib.adapter.BaseViewHolder;
import com.yang.me.lib.adapter.OnItemClickListener;
import com.yang.me.lib.bean.BaseBean;
import com.yang.me.lib.databinding.ItemSingleRvBinding;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleRvVH<T extends BaseBean, SubVH extends BaseViewHolder<SubT>, SubT extends BaseBean>
        extends BaseAutoBindVH<ItemSingleRvBinding, T> {
    protected final BaseWrapAdapter<SubVH, SubT> mAdapter;

    protected List<SubT> data = new ArrayList<>();

    public SingleRvVH(ViewGroup parent) {
        super(R.layout.item_single_rv, parent);
        mViewBinding.rv.setLayoutManager(getLayoutManager());
        mAdapter = new BaseWrapAdapter<SubVH, SubT>(data, getVhProvider()) {
            @Override
            public void onBindViewHolder(@NonNull SubVH holder, int position) {
                super.onBindViewHolder(holder, position);
                SingleRvVH.this.onBindViewHolder(holder, position);
            }
        };
        mViewBinding.rv.setAdapter(mAdapter);

        // item decoration
        if (mViewBinding.rv.getItemDecorationCount() == 0) {
            RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
            if (itemDecoration != null) {
                mViewBinding.rv.addItemDecoration(itemDecoration);
            }
        }
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    protected abstract BaseWrapAdapter.VhProvider<SubVH> getVhProvider();

    public void onBindViewHolder(@NonNull SubVH holder, int position) {
    }

    public void setSubItemClickListener(OnItemClickListener<SubT> mSubItemClickListener) {
        mAdapter.setClickListener(mSubItemClickListener);
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }
}
