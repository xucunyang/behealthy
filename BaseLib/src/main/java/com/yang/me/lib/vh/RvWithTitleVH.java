package com.yang.me.lib.vh;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yang.me.lib.adapter.BaseAutoBindVHAdapter;
import com.yang.me.lib.adapter.OnItemClickListener;
import com.yang.me.lib.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * Description: 带有标题而且包含recyclerview的ViewHolder
 *
 * Author: xucunyang
 * Time: 2021/4/28 17:12
 *
 * @param <VDB> 指定整个ViewHolder所对应的{@link ViewDataBinding}类型
 * @param <T> 最外层ViewHolder对应的Bean类型
 * @param <SubVhT> 子ViewHolder对应的Bean数据类型
 * @param <SubVh> 子ViewHolder对应的具体类型
 *
 * </pre>
 */
public abstract class RvWithTitleVH<VDB extends ViewDataBinding, T extends BaseBean, SubVhT extends BaseBean, SubVh extends BaseViewHolder<SubVhT>>
        extends BaseAutoBindVH<VDB, T> {
    private final String TAG = getClass().getSimpleName();

    protected TextView mTitle;

    protected ImageView mMore;

    protected RecyclerView mRecyclerView;

    private final List<SubVhT> mData = new ArrayList<>();

    protected BaseAutoBindVHAdapter<SubVh, SubVhT> mAdapter;

    private OnItemClickListener<SubVhT> mSubItemClickListener;

    public RvWithTitleVH(int layoutId, ViewGroup parent) {
        super(layoutId, parent);
        bindView();
        initViews();
    }

    private void initViews() {
        mAdapter = new BaseAutoBindVHAdapter<SubVh, SubVhT>(mData) {

            @NonNull
            @Override
            public SubVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return RvWithTitleVH.this.onCreateViewHolder(parent, viewType);
            }

        };
        // layout manager
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null) {
            layoutManager.setAutoMeasureEnabled(true);
            mRecyclerView.setLayoutManager(layoutManager);
        }
        // item decoration
        if (mRecyclerView.getItemDecorationCount() == 0) {
            RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
            if (itemDecoration != null) {
                mRecyclerView.addItemDecoration(itemDecoration);
            }
        }
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    public void setRecyclerViewData(List<SubVhT> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        mAdapter.updateData(mData);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    public void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        } else {
            Log.w(TAG, "setTitle: title view is null");
        }
    }

    public void setMoreClickListener(View.OnClickListener listener) {
        if (mMore != null) {
            mMore.setOnClickListener(listener);
        } else {
            Log.w(TAG, "setMoreClickListener: mMore is null");
        }
    }

    public void setSubItemClickListener(OnItemClickListener<SubVhT> mSubItemClickListener) {
        mAdapter.setClickListener(mSubItemClickListener);
    }

    public Context getContext() {
        return context;
    }

    /**
     * 由于可能出现{@link RvWithTitleVH#mTitle}在布局xml中的id不一致，所以此处重新指定
     */
    protected abstract void bindView();

    /**
     * 指定具体子ViewHolder类型
     *
     * @param parent   parent
     * @param viewType viewType
     * @return SubVh
     */
    protected abstract SubVh onCreateViewHolder(ViewGroup parent, int viewType);

}
