package com.yang.myapplication2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.locks.ReentrantLock

@Route(path = "/app2/main")
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
//        testReentrantLock()
        testSynchronize()
    }

    private fun testReentrantLock() {
        log("start")
        val fairLock = ReentrantLock(true)
        val objLock = Object();
        for (i in 0..19) {
            Thread {
                try {
                    log("-------> 开始申请 :$i")
                    fairLock.lock()
                    log("-------> 已获得锁 :$i 计算取余2是否为1${i % 2 == 1}")
                } finally {
                    fairLock.unlock()
                }
            }.start()
        }
        log("end")
    }

    private fun testSynchronize() {
        log("start")
        val objLock = Object()
        for (i in 0..19) {
            Thread {
                log("-------> 开始申请 :$i")
                synchronized(objLock) {
                    log("-------> 已获得锁 :$i 计算取余2是否为1${i % 2 == 1}")
                }
            }.start()
        }
        log("end")
    }

    private fun test(index: Int) {
        log("--------------- > Task :$index")
    }

    fun log(msg: String) {
        val time = SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS").format(Date())
        val threadName = Thread.currentThread().name
        println("$time-[$threadName]: $msg")
    }
}