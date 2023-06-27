package com.yang.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yang.myapplication.databinding.ActivityMainBinding
import com.yang.myapplication.proxy.LogHandler
import com.yang.myapplication.proxy.UserManager
import com.yang.myapplication.proxy.UserManagerImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.lang.reflect.Proxy

class MainActivity :
    BaseActivity<ActivityMainBinding?>() {
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.i(TAG, "dispatchTouchEvent: " + event.action)
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(TAG, "onTouchEvent: " + event.action)
        return super.onTouchEvent(event)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {

        testCoroutine()

        mViewBinding!!.button.setOnClickListener { v: View? ->
            Log.i(TAG, "button: ")
            Log.i(TAG, "init: ssss start")
            //            startActivity(new Intent(MainActivity.this, SecActivity.class));
            val httpUrl =
                Util.isHttpUrl(Util.PROD_URL) // 原因找到了！！！
            Log.i(TAG, "init: $httpUrl")
        }
        //        mViewBinding.linerlayout.setOnClickListener(v -> {
//            Log.i(TAG, "linerlayout: ");
//            startActivity(new Intent(MainActivity.this, SecActivity.class));
//        });
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        mViewBinding!!.button2.setOnClickListener { v: View? ->
            AlertDialog.Builder(this@MainActivity)
                .setMessage("111")
                .create()
                .show()
        }
        mViewBinding!!.button2.setOnClickListener { v: View? ->
            Log.d("xcyxch", "onclick --->")
            //            startActivity(new Intent(MainActivity.this, ThirdActivity.class));
            Glide.with(this@MainActivity).asBitmap()
                .load("https://upload.jianshu.io/users/upload_avatars/2761586/b8d3cd7925eb.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/90/h/90/format/webp")
                .into(object : SimpleTarget<Bitmap?>() {

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        Log.d(TAG, "onLoadFailed ")
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        Util.showShortCut(
                            this@MainActivity,
                            resource,
                            "groupId_001",
                            "group_nickName",
                            "nickname"
                        )
                    }
                })
        }
//        mViewBinding.test.setOnClickListener { v: View? ->
//            startActivity(
//                Intent(
//                    this@MainActivity,
//                    FourthActivity::class.java
//                )
//            )
//        }
        val proxyClass = Proxy.getProxyClass(
            UserManager::class.java.classLoader,
            *arrayOf<Class<*>>(
                UserManager::class.java
            )
        )

        WeakReference<Activity>(this, ReferenceQueue())
//        UserManager userManager = new UserManagerProxy(new UserManagerImpl());
//        userManager.addUser("xcy");
//        userManager.deleteUser("xcy");
        val userManager =
            LogHandler()
                .newInstance(UserManagerImpl()) as UserManager
        userManager.addUser("xcy")
        userManager.deleteUser("xcy")
        val myViewModel =
            ViewModelProvider(this@MainActivity).get(
                MyViewModel::class.java
            )
        Log.i(TAG, "init myViewModel: " + getObjName(myViewModel))
    }

    private fun testCoroutine() {
//        testCoroutineRunBlocking()

        testGlobalScopeLaunch()
    }

    private fun testGlobalScopeLaunch() {
//        testCoroutineStart()
//
//        testAsync()

//        testWithContext()

//        testWithContextLeeDuo()

//        testChannelInCoroutines()

        testJobJoin()
    }

    private fun testJobJoin() {
        GlobalScope.launch {
            val myJob1: Job = GlobalScope.launch {//GlobalScope.launch开启协程 协程不阻塞当前线程 并计时1s
                delay(2000)
                log("Kotlin Coroutines 111")
            }
            val myJob2: Job = GlobalScope.launch {//GlobalScope.launch开启协程 协程不阻塞当前线程 并计时1s
                delay(1500)
                log("Kotlin Coroutines 222")
            }
            val myJob3: Job = GlobalScope.launch {//GlobalScope.launch开启协程 协程不阻塞当前线程 并计时1s
                delay(1000)
                log("Kotlin Coroutines 333")
            }
            log("hello")// 主线程继续执行 输出hello
//            myJob1.join() // myJob.join() 挂起协程(GlobalScope.launch创建的协程) 直到myJob执行完毕
//            myJob2.join() // myJob.join() 挂起协程(GlobalScope.launch创建的协程) 直到myJob执行完毕
//            myJob3.join() // myJob.join() 挂起协程(GlobalScope.launch创建的协程) 直到myJob执行完毕
            log("world")
        }
    }

    private fun testChannelInCoroutines() {
        // 创建
        val channel = Channel<Int>()

        val producer = GlobalScope.launch {
            var i = 0
            while (!channel.isClosedForSend) {
                log("begin send channel isClosedForSend ${channel.isClosedForSend}")
                // 发送
                channel.send(i++)
                delay(1000)
                // channel不需要时要及时关闭
                if (i == 5) {
                    channel.close()
                    log("after close send channel isClosedForSend ${channel.isClosedForSend}")
                    return@launch
                }
            }
        }

        // 写法1:常规
//        val consumer = GlobalScope.launch {
//            while (!channel.isClosedForReceive) {
//                // 接收
//                val element = channel.receive()
//                if (element == 9) {
//                    break;
//                }
//                log("liduo", "$element")
//            }
//        }

        // 写法2:迭代器
//        val consumer = GlobalScope.launch {
//            val iterator = channel.iterator()
//            while(iterator.hasNext()){
//                // 接收
//                val element = iterator.next()
//                log("liduo", "$element")
//            }
//        }

        // 写法3:增强for循环
        val consumer = GlobalScope.launch {
            for (element in channel) {
                log("liduo", "$element")
            }
        }


        // 启动协程，返回一个接收Channel
        val receiveChannel: ReceiveChannel<Int> = GlobalScope.produce {
            while (true) {
                delay(100)
                // 发送
                send(1)
            }
        }
    }

    private fun testCoroutineStart() {
        val jobLazy = GlobalScope.launch(context = Dispatchers.Main, start = CoroutineStart.LAZY) {
            log("testGlobalScopeLaunch inner LAZY")
        }
        log("before start job lazy")

        val jobDefault =
            GlobalScope.launch(context = Dispatchers.Main, start = CoroutineStart.DEFAULT) {
                log("testGlobalScopeLaunch inner DEFAULT")
            }
        log("below start job default")

        jobDefault.start()
        jobLazy.start()
    }

    private fun testAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            log("before 声明 async")
            val deferred1 = GlobalScope.async {
                log("testGlobalScopeLaunch async inner 11")
                delay(200)
                3
            }
            val deferred2 = GlobalScope.async {
                log("testGlobalScopeLaunch async inner 22")
                delay(200)
                3
            }
            val deferred3 = GlobalScope.async {
                log("testGlobalScopeLaunch async inner 33")
                delay(200)
                3
            }
            log("before invoke await")
            log("deferred1.await(): ${deferred1.await()}")
            log("deferred2.await(): ${deferred2.await()}")
            log("deferred3.await(): ${deferred3.await()}")

        }
    }

    private fun testWithContext() {
        GlobalScope.launch {
            val result1 = withContext(Dispatchers.IO) {
                log("testGlobalScopeLaunch withContext1 inner 11")
                delay(200)
                3
            }
            val r2 = withContext(Dispatchers.IO) {
                log("testGlobalScopeLaunch withContext1 inner 22")
                delay(200)
                3
            }
            val r3 = withContext(Dispatchers.IO) {
                log("testGlobalScopeLaunch withContext1 inner 33")
                delay(200)
                3
            }
            log("before invoke with context")
            log("withContext1: $result1")
            log("withContext2: $r2")
            log("withContext3: $r3")

        }
    }

    private fun testWithContextLeeDuo() {
        // IO线程启动并执行，启动模式DEFAULT
        GlobalScope.launch(Dispatchers.IO) {
            log("liduo", "start")
            // 线程主切换并挂起，泛型可省略，自动推断
            val result = withContext<String>(Dispatchers.Main) {
                // 网络请求
                log("Dispatchers.main")
                "json data"
            }
            // 切换回IO线程
            log("liduo", result)
        }
    }


    private fun testCoroutineRunBlocking() {
        log("before blocking")
        runBlocking {
            log("in blocking")
            //            val coroutineScope = CoroutineScope(Dispatchers.Main)
            //            var job = CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            launch(Dispatchers.IO) {
                log("scope io")
                launch(Dispatchers.Main) {
                    log("scope mian")

                }
                //            }
            }
            //            job.join()
        }
    }

    fun log(vararg msg: String?) {
        Log.d("coroutine-log", "[${Thread.currentThread().name}]:${msg.toList()}")
    }
}