package com.yang.myapplication.aop

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.util.*
import kotlin.streams.toList

/**
 * Created by LanceWu on 2022/9/6
 *
 * 函数切面
 */
@Aspect
class MethodAspect {

//    @Around("call(* com.lancewu.aspectj.MainActivity.toast())")
//    fun around_MainActivity_toast(joinPoint: ProceedingJoinPoint): Any? {
//        aroundOldResult(joinPoint)
//        val context = joinPoint.`this` as Context
//        Toast.makeText(context, "代码织入toast", Toast.LENGTH_LONG).show()
//        return null
//    }

    private fun aroundOldResult(joinPoint: ProceedingJoinPoint): Any? {
        try {
            return joinPoint.proceed(joinPoint.args)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
        return null
    }

    @Pointcut("set(int com.yang.myapplication.aop.RvTest.testCount)")
    fun onSetTestCount() {
    }

    @Around("onSetTestCount()")
    fun aroundOnSetTestCount(joinPoint: ProceedingJoinPoint): Any? {
        val rvTest = joinPoint.target as RvTest
        Log.d("MethodAspect", "aroundOnSetTestCount before old:${rvTest.count}, new:${joinPoint.args[0]}")
        val aroundOldResult = aroundOldResult(joinPoint)
        Log.d("MethodAspect", "aroundOnSetTestCount after ${rvTest.count}, new:${joinPoint.args[0]}")
        return aroundOldResult
    }

//    @Around("setOn1Click()")
//    fun aroundSetOnClick(joinPoint: ProceedingJoinPoint): Any? {
//        Log.d("MethodAspect", "点击拦截")
//        return aroundOldResult(joinPoint)
//    }

    @Around("call(* android.widget.TextView.setText(java.lang.CharSequence))")
    fun around_TextView_setText(joinPoint: ProceedingJoinPoint): Any? {
        val text = joinPoint.args[0]
        Log.d("MethodAspect", "around_TextView_setText : $text")
        return aroundOldResult(joinPoint)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @Around("call(* androidx.recyclerview.widget.RecyclerView.onEnterLayoutOrScroll(..))")
    fun around_Rv_(joinPoint: ProceedingJoinPoint): Any? {
//        val text = joinPoint.args[0]

        val str = Thread.currentThread().stackTrace.toList()
            .filter { it.methodName.isNotBlank() }
            .take(10)  // 取前10个调用栈
            .joinToString(separator = "\n") { element ->
                // 格式化为：类名.方法名(文件名:行号)
                "${element.className}.${element.methodName}(${element.fileName}:${element.lineNumber})"
            }
        Log.d("MethodAspect", "onEnterLayoutOrScroll : ${str}")
        return aroundOldResult(joinPoint)
    }
}