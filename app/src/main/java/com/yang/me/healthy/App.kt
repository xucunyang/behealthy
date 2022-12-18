package com.yang.me.healthy

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.AndroidExcludedRefs
import com.squareup.leakcanary.LeakCanary
import com.wonderkiln.blurkit.BlurKit
import timber.log.Timber

class App : Application() {

    companion object {
        private lateinit var context: Context

        fun getContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        BlurKit.init(this)

        //因调试设备非正常手机（出现泄漏屏幕中间会出现小鸟），所以无法使用notification或者leaks入口查看泄漏日志，所以采用保存本地的方法
//        LeakCanary.refWatcher(this).listenerServiceClass(
//            LeakService.class)
//                .excludedRefs(AndroidExcludedRefs.createAppDefaults().build())
    }

}