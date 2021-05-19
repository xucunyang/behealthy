package com.yang.me.lib;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yang.me.lib.adapter.BaseViewHolder;
import com.yang.me.lib.bean.BaseBean;
import com.yang.me.lib.databinding.FragmentListWithBarBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * Description: 带标题toolbar的列表页面基类
 *
 * Author: xucunyang
 * Time: 2021/4/28 18:30
 *
 * </pre>
 */
public abstract class BaseListFragment<VH extends BaseViewHolder<T>, T extends BaseBean>
        extends BaseBindFragment<FragmentListWithBarBinding> {

    protected List<T> mData = new ArrayList<>();

    protected BaseWrapAdapter<VH, T> mAdapter;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_list_with_bar;
    }

    @Override
    protected void init() {
        setImmersiveStatusBar(mViewBinding.getRoot());
        setDarkStatusBarTextColor();
        initToolbar();
        mViewBinding.recyclerView.setLayoutManager(getLayoutManager());
        mAdapter = getAdapter();
        mViewBinding.recyclerView.setAdapter(mAdapter);
        loadListData();
    }

    @NotNull
    protected BaseWrapAdapter<VH, T> getAdapter() {
        return new BaseWrapAdapter<VH, T>(mData, getVhProvider()) {
            @Override
            public int getItemViewType(int position) {
                return BaseListFragment.this.getItemViewType(position);
            }
        };
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    public void loadListData() {

    }

    protected int getItemViewType(int position) {
        return 0;
    }

    protected abstract void initToolbar();

    protected abstract BaseWrapAdapter.VhProvider<VH> getVhProvider();

}
