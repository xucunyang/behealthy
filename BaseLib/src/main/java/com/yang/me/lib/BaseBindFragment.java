package com.yang.me.lib;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    protected final String TAG = getLogTag();

    protected ViewBinding mViewBinding;

    protected View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        mViewBinding = DataBindingUtil.inflate(inflater, getFragmentLayoutId(), container, false);
        mRootView = mViewBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
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

    /**
     * 沉浸状态栏
     *
     * @param toolbar                 toolbar
     * @param blurView                blurView
     * @param statusBarBlackFontColor 状态栏字体颜色
     */
    protected void setImmersiveBlurView(View toolbar, View blurView, boolean statusBarBlackFontColor) {
        Util.setViewLayoutParams(toolbar, LinearLayout.LayoutParams.MATCH_PARENT, Util.getTopBarHeight(getContext()));

        blurView.setPadding(0, Util.getStatusBarHeight(getResources()), 0, 0);

        Util.setAndroidNativeLightStatusBar(getActivity(), statusBarBlackFontColor);
    }

    protected abstract int getFragmentLayoutId();

    protected abstract void init();

    protected String getLogTag() {
        return "xcy_" + getObjName(this);
    }

    protected String getObjName(Object object) {
        return object == null ? "null" : object.getClass().getSimpleName() + "@"
                + Integer.toHexString(object.hashCode());
    }
}
