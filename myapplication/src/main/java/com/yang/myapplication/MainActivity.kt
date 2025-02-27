package com.yang.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.yang.myapplication.databinding.ActivityMainBinding
import com.yang.myapplication.proxy.LogHandler
import com.yang.myapplication.proxy.UserManager
import com.yang.myapplication.proxy.UserManagerImpl
import com.yang.myapplication2.MainActivity2
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.lang.reflect.Proxy

class MainActivity :
    BaseActivity<ActivityMainBinding>() {

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        log(TAG, "dispatchTouchEvent: " + event.action)
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(TAG, "onTouchEvent: " + event.action)
        return super.onTouchEvent(event)
    }

    override val layoutId: Int
        get() = R.layout.activity_main


    override fun init() {

        testCoroutine()

        viewBinding!!.button.setOnClickListener { v: View? ->
            println("--xcy-->> xxx")
//            testReg()
//            ARouter.getInstance().build("/app2/main").navigation()
            startActivity(Intent(MainActivity@this, SecActivity::class.java))
        }
        //        mViewBinding.linerlayout.setOnClickListener(v -> {
//            Log.i(TAG, "linerlayout: ");
//            startActivity(new Intent(MainActivity.this, SecActivity.class));
//        });
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        viewBinding!!.button2.setOnClickListener { v: View? ->
            AlertDialog.Builder(this@MainActivity)
                .setMessage("111")
                .create()
                .show()
        }
        viewBinding!!.button2.setOnClickListener { v: View? ->
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
//                        Util.showShortCut(
//                            this@MainActivity,
//                            resource,
//                            "groupId_001",
//                            "group_nickName",
//                            "nickname"
//                        )
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

    private fun testReg() {
        Log.i(TAG, "button: ")
        Log.i(TAG, "init: ssss start")
        //            startActivity(new Intent(MainActivity.this, SecActivity.class));
        val httpUrl =
            Util.isHttpUrl(Util.PROD_URL) // 原因找到了！！！
        Log.i(TAG, "init: $httpUrl")
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

//        testJobJoin()

//        testLifecycle()

//        testJobJoinCancel()
//        lifecycleScope.launch {
        val launchIn = (1..3)
            .asFlow()
            .transform {
                if (it == 2) {
                    emit("emit value is ${it * 2}")
                }

                emit("transform value is ${it}")
            }.onEach {
                log("onEach $it")
            }.launchIn(lifecycleScope)

//        }
        val mutableSharedFlow = MutableSharedFlow<String>(
            replay = 1
        )
        mutableSharedFlow.shareIn(lifecycleScope, SharingStarted.Eagerly, replay = 2)
        mutableSharedFlow.stateIn(lifecycleScope, SharingStarted.WhileSubscribed(), "initial v")
        val mutableStateFlow = MutableStateFlow("initial~~")
    }

    fun ss(value: Int) = value == 1

    private fun testLifecycle() {
        runBlocking {
            log("run blocking")
            val job = flowEvent().onEach {
                log("下游 event $it")
            }.onCompletion {
                log("onCompletion")
            }.catch {
                log("catch $it")
            }.launchIn(CoroutineScope(Dispatchers.IO))

            async {
                log("before cancel")
                delay(1100)
                job.cancel("delay 200 cancel")
                log("after cancel")
            }
            job.join()
            log("run blocking below join")
        }
//        lifecycleScope.launch {
//            flowEvent().onEach {
//                log("下游事件 $it")
//            }.launchIn(CoroutineScope(Dispatchers.IO))
//        }
    }

    suspend fun flowEvent() = (1..3)
        .asFlow()
        .onEach {
            delay(500)
            log(" 上游 event $it")
        }.flowOn(Dispatchers.Default)

    private fun testJobJoinCancel() {
        runBlocking {
            log("run blocking start")
            val job = launch(start = CoroutineStart.LAZY) {
                repeat(100) {
                    log("in repeating start -> $it")
                    delay(500)
                    log("in repeating end   -> $it")
                }
            }
            async {
                log("start delay 1100")
                delay(1300)
                log("cancel job")
//                job.cancel()
                job.join()
                log("run blocking end")
            }

        }
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
            myJob1.join() // myJob.join() 挂起协程(GlobalScope.launch创建的协程) 直到myJob执行完毕
            myJob2.join() // myJob.join() 挂起协程(GlobalScope.launch创建的协程) 直到myJob执行完毕
            myJob3.join() // myJob.join() 挂起协程(GlobalScope.launch创建的协程) 直到myJob执行完毕
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
        Log.d(TAG, "[${Thread.currentThread().name}]:${msg.toList()}")
    }
}