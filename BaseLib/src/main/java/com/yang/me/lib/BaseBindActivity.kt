package com.yang.me.lib;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.yang.me.lib.util.StatusBarUtil;
import com.yang.me.lib.util.Util;

/**
 * <pre>
 * Description: 自动绑定基类
 *
 * Author: xucunyang
 * Time: 2021/4/8 17:46
 * </pre>
 */
public abstract class BaseBindActivity<ViewBinding extends ViewDataBinding> extends AppCompatActivity {

    protected ViewBinding mViewBinding;

    @Override
    public void onCreate(Bundle bundle) {
        initStatusBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onCreate(bundle);
        }
        mViewBinding = DataBindingUtil.setContentView(this, getLayoutId());
        init();
    }

    protected void initStatusBar() {

    }

    protected void initStatusBarHeight() {
        StatusBarUtil.setStatusBarLightMode(this, getResources().getColor(getStatusBarColorId()));
    }

    protected void setImmersiveStatusBar(View topLayout) {
        if (topLayout != null) {
            topLayout.setPadding(topLayout.getPaddingStart(), Util.getStatusBarHeight(getResources()),
                    topLayout.getPaddingEnd(), topLayout.getPaddingBottom());
        }
    }

    protected int getStatusBarColorId() {
        return R.color.white;
    }

    protected abstract int getLayoutId();

    protected abstract void init();

}
