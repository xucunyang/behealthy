package com.yang.me.lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.yang.me.lib.bean.BaseBean;
import com.yang.me.lib.util.SizeUtils;
import com.yang.me.lib.util.Util;


public abstract class BaseAutoBindVH<VDB extends ViewDataBinding, T extends BaseBean> extends BaseViewHolder<T> {

    protected VDB mViewBinding;

    protected OnItemClickListener<T> mItemClickListener;

    protected Context context;

    public BaseAutoBindVH(Context context, @NonNull VDB viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.mViewBinding = viewDataBinding;
        this.context = context;
        initView();
    }

    public BaseAutoBindVH(int layoutId, ViewGroup parent) {
        this(parent.getContext(), (VDB) getViewDataBinding(parent.getContext(), layoutId, parent));
    }

    @Override
    public void onBindVH(int position, T bean) {
        refreshUi(position, bean);
    }

    public void refreshUi(final int i, final T baseBean) {
        Util.setViewLayoutParams(itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (mItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(itemView, i, (T) baseBean);
                }
            });
        }
    }

    protected T castBean(BaseBean baseBean) {
        if (baseBean != null) {
            return ((T) baseBean);
        }
        return null;
    }

    protected void loadImage(ImageView imageView, String url) {
        Glide.with(context).load(url).into(imageView);
    }

    protected void setText(TextView textView, String text) {
        textView.setText(text);
    }

    /**
     * 初始化view，子view需要处理的可以重写
     */
    protected void initView() {
    }


    public OnItemClickListener<T> getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener<T> listener) {
        this.mItemClickListener = listener;
    }

    protected void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    protected int dp2px(float dp) {
        return (int) SizeUtils.dp2px(context, dp);
    }

    /**
     * databinding工具方法，意在构造方法里即可看到ViewHolder的布局R.layout.xx
     *
     * @param context  context
     * @param layoutId 布局
     * @param parent   父布局
     * @param <T>      泛型具体的ViewDataBinding
     * @return 体的ViewDataBinding
     */
    protected static <T extends ViewDataBinding> T getViewDataBinding(Context context, int layoutId, ViewGroup parent) {
        return DataBindingUtil.<T>inflate(LayoutInflater.from(context), layoutId, parent, false);
    }

}
