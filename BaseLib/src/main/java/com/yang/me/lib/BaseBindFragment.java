package com.yang.me.lib;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.yang.me.lib.util.StatusBarUtil;
import com.yang.me.lib.util.Util;

/**
 * <pre>
 * Description: 自动绑定基类
 *
 * Author: xucunyang
 * Time: 2021/4/7 14:52
 * </pre>
 */
public abstract class BaseBindFragment<ViewBinding extends ViewDataBinding>
        extends Fragment implements View.OnClickListener {

    protected final String TAG = this.getClass().getSimpleName();

    protected ViewBinding mViewBinding;

    protected View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewBinding = DataBindingUtil.inflate(inflater, getFragmentLayoutId(), container, false);
        mRootView = mViewBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    /**
     * 设置系统状态栏颜色
     *
     * @param colorResId 颜色
     */
    protected void setStatusBarColor(int colorResId) {
        Context context = getContext();
        if (context != null) {
            StatusBarUtil.setStatusBarColor(getActivity(), context.getResources().getColor(colorResId));
        }
    }

    protected void setImmersiveStatusBar(View topLayout) {
        if (topLayout != null) {
            topLayout.setPadding(topLayout.getPaddingStart(), Util.getStatusBarHeight(getResources()),
                    topLayout.getPaddingEnd(), topLayout.getPaddingBottom());
        }
    }

    protected void setDarkStatusBarTextColor() {
        if (getActivity() != null && getActivity().getWindow() != null) {
            View decor = getActivity().getWindow().getDecorView();
            int systemUiVisibility = decor.getSystemUiVisibility();
            if (systemUiVisibility == (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)) {
                setImmersiveStatusBar(decor);
            } else {
                Util.setAndroidNativeLightStatusBar(getActivity(), true);
            }
        }
    }

    @Override
    public void onClick(View v) {
    }

    protected abstract int getFragmentLayoutId();

    protected abstract void init();

}
