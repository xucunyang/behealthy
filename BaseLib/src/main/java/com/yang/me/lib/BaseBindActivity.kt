package com.yang.me.lib

import androidx.databinding.ViewDataBinding
import android.os.Bundle
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yang.me.lib.util.StatusBarUtil
import com.yang.me.lib.R
import com.yang.me.lib.util.Util

/**
 * <pre>
 * Description: 自动绑定基类
 *
 * Author: xucunyang
 * Time: 2021/4/8 17:46
</pre> *
 */
abstract class BaseBindActivity<ViewBinding : ViewDataBinding> : AppCompatActivity() {
    protected val viewBinding: ViewBinding by lazy {
        DataBindingUtil.setContentView<ViewBinding>(this, layoutId)
    }
    public override fun onCreate(bundle: Bundle?) {
        initStatusBar()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onCreate(bundle)
        }
        init()
    }

    protected fun initStatusBar() {}
    protected fun initStatusBarHeight() {
        StatusBarUtil.setStatusBarLightMode(this, resources.getColor(statusBarColorId))
    }

    protected fun setImmersiveStatusBar(topLayout: View?) {
        topLayout?.setPadding(
            topLayout.paddingStart, Util.getStatusBarHeight(resources),
            topLayout.paddingEnd, topLayout.paddingBottom
        )
    }

    protected val statusBarColorId: Int
        protected get() = R.color.white
    protected abstract val layoutId: Int
    protected abstract fun init()
}