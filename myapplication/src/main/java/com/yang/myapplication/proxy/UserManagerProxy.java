package com.yang.myapplication.proxy;

import android.util.Log;

public class UserManagerProxy implements UserManager {
    private static final String TAG = "UserManagerProxy";
    private UserManager mUserManager;

    public UserManagerProxy(UserManager userManager) {
        mUserManager = userManager;
    }

    @Override
    public void addUser(String user) {
        Log.i(TAG, "before addUser: ");
        if (mUserManager != null) {
            mUserManager.addUser(user);
        }
        Log.i(TAG, "after addUser: ");
    }

    @Override
    public void deleteUser(String user) {
        Log.i(TAG, "before deleteUser: ");
        if (mUserManager != null) {
            mUserManager.deleteUser(user);
        }
        Log.i(TAG, "after deleteUser: ");
    }
}
