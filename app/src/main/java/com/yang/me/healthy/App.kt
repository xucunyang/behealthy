package com.yang.me.healthy

import android.app.Application
import android.content.Context
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
    }

}