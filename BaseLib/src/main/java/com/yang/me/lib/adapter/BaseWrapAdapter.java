package com.yang.me.lib.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.yang.me.lib.vh.BaseViewHolder;
import com.yang.me.lib.bean.BaseBean;

import java.util.List;

public class BaseWrapAdapter<VH extends BaseViewHolder<T>, T extends BaseBean>
        extends BaseAutoBindVHAdapter<VH, T> {

    private VhProvider<VH> mVhProvider;

    public BaseWrapAdapter(List<T> data, @NonNull VhProvider<VH> provider) {
        super(data);
        this.mVhProvider = provider;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  mVhProvider.createVH(parent, viewType);
    }

    public VhProvider<VH> getVhProvider() {
        return mVhProvider;
    }

    public void setVhProvider(VhProvider<VH> mVhProvider) {
        this.mVhProvider = mVhProvider;
    }

    public interface VhProvider<VH extends BaseViewHolder<?>> {

        VH createVH(@NonNull ViewGroup parent, int viewType);

    }
}
