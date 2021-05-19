package com.yang.me.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yang.me.lib.databinding.ActivityDisplayFragmentBinding;

/**
 * <pre>
 * Description: 展示activity用的宿主;
 * 调用{@link #start(Context, Class)}方法，传入需要展示的Fragment的class，
 * singleInstance启动模式，支持启动多次展示多个fragment支持返回
 *
 * Author: xucunyang
 * Time: 2021/3/4 15:45
 * </pre>
 */
public class FragmentDisplayActivity extends BaseBindActivity<ActivityDisplayFragmentBinding> {
    private static final String TAG = "FragmentDisplayActivity";

    public static final String NOT_FOUND_FRAGMENT_PATH = "com.android.jsbcmasterapp.fragment.NotFoundFragment";

    private static final String KEY_FRAGMENT_NAME = "fragment_name";
    /**
     * 参数
     */
    private static final String KEY_ARGUMENT = "argument";
    /**
     * 沉浸式状态栏
     */
    private static final String KEY_STATUS_BAR_IMMERSIVE = "status_bar_immersive";

    private FragmentManager fragmentManager;

    private String mFragmentPath;

    private FragmentTransaction mFragmentTransaction;

    private boolean fromNewIntent = false;

    private Bundle argument;

    private BaseBindFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mFragmentPath = getIntent().getStringExtra(KEY_FRAGMENT_NAME);
            argument = getIntent().getBundleExtra(KEY_ARGUMENT);

            fromNewIntent = false;

            super.onCreate(savedInstanceState);
        }
        Log.i(TAG, "onCreate: ");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_display_fragment;
    }

    @Override
    protected void init() {
        Log.i(TAG, "initData: ");
        if (TextUtils.isEmpty(mFragmentPath)) {
            Log.w(TAG, "initData: fragmentPath is empty");
            finish();
            return;
        }

        Log.i(TAG, "initData: show fragment:" + mFragmentPath);

        try {
            Class<?> clazz = Class.forName(mFragmentPath);
            fragment = (BaseBindFragment) clazz.newInstance();
            fragment.setArguments(argument);
            fragmentManager = getSupportFragmentManager();
            mFragmentTransaction = fragmentManager.beginTransaction();
            if (fromNewIntent) {
                mFragmentTransaction.setCustomAnimations(
                        R.anim.slide_enter,
                        R.anim.slide_exit,
                        R.anim.pop_left_enter,
                        R.anim.pop_left_exit
                );
            }
            mFragmentTransaction.replace(R.id.frame_layout, fragment);
            mFragmentTransaction.addToBackStack(mFragmentPath);
            mFragmentTransaction.commit();
        } catch (ClassNotFoundException e) {
            Log.w(TAG, "initData: ClassNotFoundException:" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.w(TAG, "initData: IllegalAccessException:" + e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            Log.w(TAG, "initData: InstantiationException:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i(TAG, "onNewIntent: " + intent);

        fromNewIntent = true;
        if (intent != null) {
            mFragmentPath = intent.getStringExtra(KEY_FRAGMENT_NAME);
            argument = intent.getBundleExtra(KEY_ARGUMENT);
            init();
        } else {
            Log.i(TAG, "onNewIntent: intent is null");
        }
    }

    @Override
    public void onBackPressed() {
        Log.w(TAG, "onBackPressed: " + fragmentManager.getBackStackEntryCount());
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(0, 0);
        mFragmentTransaction.commit();

        super.finish();

        overridePendingTransition(R.anim.pop_left_enter, R.anim.pop_left_exit);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_enter, R.anim.activity_start_outgoing);
    }

    private static Class<? extends Fragment> getNotFoundFragmentClass() {
        Class<? extends Fragment> fragClass = null;
        try {
            fragClass = (Class<? extends Fragment>) Class.forName(NOT_FOUND_FRAGMENT_PATH);
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return fragClass;
    }

    public static void start(Context context, Class<? extends Fragment> fragmentCls) {
        start(context, fragmentCls, null, false);
    }

    public static void start(Context context, String fragClassName, Bundle argument,
                             boolean immersiveStatusBar) {
        Class<? extends Fragment> fragClass;
        try {
            Class<?> originClass = Class.forName(fragClassName);
            fragClass = (Class<? extends Fragment>) originClass;
        } catch (ClassNotFoundException | ClassCastException e) {
            fragClass = getNotFoundFragmentClass();
            e.printStackTrace();
        }
        start(context, fragClass, argument, immersiveStatusBar);
    }

    /**
     * 展示fragment
     *
     * @param context     上下文
     * @param fragmentCls fragment
     * @param argument    argument
     */
    public static void start(Context context, Class<? extends Fragment> fragmentCls, Bundle argument,
                             boolean immersiveStatusBar) {
        if (fragmentCls != null) {
            Log.i(TAG, "start: " + fragmentCls.getName());
            Intent intent = new Intent(context, FragmentDisplayActivity.class);
            intent.putExtra(KEY_FRAGMENT_NAME, fragmentCls.getName());
            intent.putExtra(KEY_STATUS_BAR_IMMERSIVE, immersiveStatusBar);
            if (argument != null) {
                intent.putExtra(KEY_ARGUMENT, argument);
            }
            context.startActivity(intent);

            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(R.anim.slide_enter, R.anim.activity_start_outgoing);
            } else {
                Log.w(TAG, "start: context is not instance of Activity, " + context);
            }
        } else {
            Log.w(TAG, "start: fragment is null");
        }
    }
}
