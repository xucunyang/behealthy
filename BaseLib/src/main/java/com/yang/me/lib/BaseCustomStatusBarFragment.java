package com.yang.me.lib;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.yang.me.lib.util.Util;

/**
 * <pre>
 *
 * Description: 指定导航栏是否显示内容
 *
 * Author: xucunyang
 * Time: 2021/4/29 18:17
 *
 * </pre>
 */
public abstract class BaseCustomStatusBarFragment<V extends ViewDataBinding> extends BaseBindFragment<V> {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Log.w(TAG, "onViewCreated: " + isFullScreen() + ", " + isFullScreen2());

        if (needFullScreen()) {
            View decor = getActivity().getWindow().getDecorView();
            int systemUiVisibility = decor.getSystemUiVisibility();
            if (systemUiVisibility == (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)) {
                Log.w(TAG, "onViewCreated:setImmersiveStatusBar ");
                setImmersiveStatusBar(view);
            } else {
                Log.w(TAG, "onViewCreated:setAndroidNativeLightStatusBar ");
                Util.setAndroidNativeLightStatusBar(getActivity(), true);
            }
        } else {
            view.setPadding(0, 0, 0, 0);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    public boolean isFullScreen() {
        if (getActivity() != null && getActivity().getWindow() != null) {
            Window window = getActivity().getWindow();
            return ((window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    == WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        return false;
    }

    public boolean isFullScreen2() {
        if (getActivity() != null && getActivity().getWindow() != null) {
            Window window = getActivity().getWindow();
            return (window.getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_FULLSCREEN)
                    == View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        return false;
    }


    public boolean needFullScreen() {
        return false;
    }

    public boolean showDarkStatusBar() {
        return true;
    }
}
