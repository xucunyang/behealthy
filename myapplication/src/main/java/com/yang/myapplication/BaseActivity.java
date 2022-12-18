package com.yang.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.databinding.ViewDataBinding;

import com.yang.me.lib.BaseBindActivity;

import java.util.Arrays;

/**
 * <pre>
 *
 * description: 基类activity
 * author:      xucunyang@outlook.com
 * time:        2022-12-04 14:15:36
 *
 * </pre>
 */
public abstract class BaseActivity<ViewBinding extends ViewDataBinding> extends BaseBindActivity<ViewBinding> {

    protected String TAG = getTag();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    protected String getTag() {
        return "xcy_" + getObjName(this);
    }

    protected String getObjName(Object object) {
        return object == null ? "null" : object.getClass().getSimpleName() + "@"
                + Integer.toHexString(object.hashCode());
    }

}
