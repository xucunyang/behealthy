package com.yang.myapplication

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

}