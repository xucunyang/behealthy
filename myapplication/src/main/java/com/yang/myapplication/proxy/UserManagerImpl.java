package com.yang.myapplication.proxy;

import android.util.Log;

public class UserManagerImpl implements UserManager {
    private static final String TAG = "UserManagerImpl";

    @Override
    public void addUser(String user) {
        Log.d(TAG, "add user");
    }

    @Override
    public void deleteUser(String user) {
        Log.d(TAG, "delete user");
    }
}
