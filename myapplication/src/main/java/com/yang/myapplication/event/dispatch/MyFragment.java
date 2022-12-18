package com.yang.myapplication.event.dispatch;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yang.me.lib.BaseListFragment;
import com.yang.me.lib.adapter.BaseWrapAdapter;
import com.yang.me.lib.bean.SingleSelectBean;
import com.yang.me.lib.util.TestUtil;

public class MyFragment extends BaseListFragment<MyVH, SingleSelectBean> {

    public static MyFragment newInstance(String title) {
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        myFragment.setArguments(args);
        return myFragment;
    }

    private MyFragment() {
    }

    @Override
    protected void initToolbar() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            String title = arguments.getString("title");
            mViewBinding.title.setText(String.format("title: %s", title));
        }
    }

    @Override
    protected BaseWrapAdapter.VhProvider<MyVH> getVhProvider() {
        return new BaseWrapAdapter.VhProvider<MyVH>() {
            @Override
            public MyVH createVH(@NonNull ViewGroup parent, int viewType) {
                return new MyVH(parent);
            }
        };
    }

    @Override
    public void loadListData() {
        super.loadListData();
        mData.clear();
        mData.addAll(TestUtil.getSingleSelectListBean());
        mAdapter.updateData(mData);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }
}