package com.yang.me.healthy.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.yang.me.healthy.R
import com.yang.me.healthy.databinding.FragmentOtherBinding
import com.yang.me.lib.BaseBindActivity
import java.lang.IllegalStateException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ThirdActivity: BaseBindActivity<FragmentOtherBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_other

    override fun init() {
        intent?.getStringExtra("title").apply {
            viewBinding.title.text = this as String
        }
    }

    companion object {
        fun start(cxt: Context, title: String) {
            val intent = Intent(cxt, ThirdActivity::class.java)
            intent.apply {
                this.putExtra("title", title)
            }
            cxt.startActivity(intent)
        }
    }

}


fun <T> activityArgs() = ActivityArgumentProperty<T>()

class ActivityArgumentProperty<T>: ReadWriteProperty<Activity, T> {
    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        Log.d("xcy", "getvalue ${property.name}")
        return thisRef.intent?.extras?.get(property.name) as? T
            ?: throw IllegalStateException("prop ${property.name} could not be read")
    }

    override fun setValue(thisRef: Activity, property: KProperty<*>, value: T) {
        val intent = thisRef.intent ?: Intent()
        var bundle = Bundle()
//        bundle["aa"] = 1
//        intent.putExtras(bundle) = value
    }
}