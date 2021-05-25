package com.yang.me.lib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseDialog<VDB extends ViewDataBinding> extends Dialog {

    protected VDB mViewBinding;

    protected View mRootView;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayoutId(), null, false);
        mRootView = mViewBinding.getRoot();
        setContentView(mRootView);

        initView();
    }

    public void initView() {
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = this.getWindow();
        if (window != null) {
            WindowManager windowManager = window.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            Point outSize = new Point();
            display.getSize(outSize);
            window.setGravity(80);
            window.setSoftInputMode(16);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    protected abstract int getLayoutId();

}
