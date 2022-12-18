package com.yang.myapplication;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.yang.myapplication.databinding.ActivityMainBinding;
import com.yang.myapplication.proxy.LogHandler;
import com.yang.myapplication.proxy.UserManager;
import com.yang.myapplication.proxy.UserManagerImpl;

import java.lang.reflect.Proxy;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent: " + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mViewBinding.button.setOnClickListener(v -> {
            Log.i(TAG, "button: ");
            startActivity(new Intent(MainActivity.this, SecActivity.class));
        });
//        mViewBinding.linerlayout.setOnClickListener(v -> {
//            Log.i(TAG, "linerlayout: ");
//            startActivity(new Intent(MainActivity.this, SecActivity.class));
//        });
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        mViewBinding.button2.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("111")
                    .create()
                    .show();
        });

        Class<?> proxyClass = Proxy.getProxyClass(UserManager.class.getClassLoader(), new Class<?>[]{UserManager.class});


//        UserManager userManager = new UserManagerProxy(new UserManagerImpl());
//        userManager.addUser("xcy");
//        userManager.deleteUser("xcy");
        UserManager userManager = (UserManager) new LogHandler().newInstance(new UserManagerImpl());
        userManager.addUser("xcy");
        userManager.deleteUser("xcy");


        MyViewModel myViewModel = new ViewModelProvider(MainActivity.this).get(MyViewModel.class);
        Log.i(TAG, "init myViewModel: " + getObjName(myViewModel));
    }

}