## Android



### MVC & MVP & MVVM
* MVC
> 优点：视图与模型分离，通过控制器实现交互，低耦合高复用
> 缺点：在Android开发中，activity既是视图也是控制器，业务界面复杂时相当臃肿不变维护扩展，职责不明确，v可以直接操作m耦合性高

* MVP
> 优点：通过p将m和v完全分离，activity只负责显示和用户交互，m和p交互，v和p交互，职责明确，代码简洁。复杂逻辑放到presenter纽带。便于单元测试通过接口交互
> 缺点：

* MVVM
> 优点：更加释放m和v的压力，数据双向绑定，m和p变成viewmodel
> 缺点：

* MVVM和MVP的关系：MVVM 模式将 Presenter 改名为 ViewModel，基本上与 MVP 模式完全一致。Presenter与View的交互是通过接口来进行的。 2、唯一的区别是，MVVM采用双向绑定（data-binding）：View的变动，自动反映在 ViewModel，反之亦然，model变动通过livedata监听发应给view。这样开发者就不用处理接收事件和View更新的工作，框架已经帮你做好了。
* MVC & MVP区别：




### Binder
fork进程 分支复制类似
效率高：内存映射一次复制，相比于管道消息队列，两次复制
稳定性：C/S结构，相比于内存共享效率虽然高，但是流程复杂难以控制
安全性：每个程序都有uid pid，恶意程序无法仿冒



### 四大组件

> + Activity
> > 1.Activity 说下Activity生命周期 Activity A 启动另一个Activity B 会调用哪些方法？
如果B是透明主题的又或则是个DialogActivity呢
说下onSaveInstanceState()方法的作用 ? 何时会被调用？
Activity的启动流程 onSaveInstanceState(),onRestoreInstanceState的掉用时机
activity的启动模式和使用场景
Activity A跳转Activity B，再按返回键，生命周期执行的顺序 横竖屏切换,按home键,按返回键,锁屏与解锁屏幕,跳转透明Activity界面,启动一个 Theme 为 Dialog 的 Activity，弹出Dialog时Activity的生命周期
onStart 和 onResume、onPause 和 onStop 的区别 Activity之间传递数据的方式Intent是否有大小限制，如果传递的数据量偏大，有哪些方案 Activity的onNewIntent()方法什么时候会执行
显示启动和隐式启动
scheme使用场景,协议格式,如何使用

> + Service
> > 2.Service service 的生命周期，两种启动方式的区别 Service的两种启动方式？区别在哪
如何保证Service不被杀死 ？
 Service与Activity怎么实现通信
IntentService是什么,IntentService原理，应用场景及其与Service的区别 Service 的 onStartCommand 方法有几种返回值?各代表什么意思?
bindService和startService混合使用的生命周期以及怎么关闭
用过哪些系统Service ？
了解ActivityManagerService吗？发挥什么作用

> + Broadcast Receiver
> > 广播的两种注册方式的区别
广播发送和接收的原理
本地广播和全局广播的区别

> + ContentProvider
> > 什么是ContentProvider及其使用
ContentProvider的权限管理
ContentProvider,ContentResolver,ContentObserver之间的关系
ContentProvider的实现原理
ContentProvider的优点
Uri 是什么



### 线程间通讯（Handler）
> Handler Handler的实现原理  
> 子线程中能不能直接new一个Handler,为什么主线程可以  
> 主线程的Looper第一次调用loop方法,什么时候,哪个类  
> Handler导致的内存泄露原因及其解决方案  
> 一个线程可以有几个Handler,几个Looper,几个MessageQueue对象  
> Message对象创建的方式有哪些 & 区别？  
> Message.obtain()怎么维护消息池的Handler  
> 有哪些发送消息的方法 Handler的post与sendMessage的区别和应用场景  
> handler postDealy后消息队列有什么变化，假设先 postDelay 10s, 再postDelay 1s, 怎么处理这2条消息  
> MessageQueue是什么数据结构 Handler怎么做到的一个线程对应一个Looper，如何保证只有一个MessageQueue ThreadLocal在Handler机制中的作用  
> HandlerThread是什么 & 好处 &原理 & 使用场景  
> IdleHandler及其使用场景 消息屏障,同步屏障机制  
> 子线程能不能更新UI  
> 为什么Android系统不建议子线程访问UI  
> Android中为什么主线程不会因为Looper.loop()里的死循环卡死  
> MessageQueue#next 在没有消息的时候会阻塞，如何恢复？  
> Handler消息机制中，一个looper是如何区分多个Handler的 当Activity有多个Handler的时候，怎么样区分当前消息由哪个Handler处理
> 处理message的时候怎么知道是去哪个callback处理的
> Looper.quit/quitSafely的区别 通过Handler如何实现线程的切换
> Handler 如何与 Looper 关联的 Looper 如何与 Thread 关联的 Looper.loop()源码 MessageQueue的enqueueMessage()方法如何进行线程同步的 MessageQueue的next()方法内部原理
> 子线程中是否可以用MainLooper去创建Handler，Looper和Handler是否一定处于一个线程
>
###  自定义 View
> * 子View创建MeasureSpec创建规则是什么
> * 自定义Viewwrap_content不起作用的原因
> * 在Activity中获取某个View的宽高有几种方法
> * 为什么onCreate获取不到View的宽高
> * View#post与Handler#post的区别
> * Android绘制和屏幕刷新机制原理
> * Choreography原理
> * 什么是双缓冲
> * 为什么使用SurfaceView
> * 什么是SurfaceView
> * View和SurfaceView的区别
> * SurfaceView为什么可以直接子线程绘制
> *  SurfaceView、TextureView、SurfaceTexture、GLSurfaceView
> * getWidth()方法和getMeasureWidth()区别
> * invalidate() 和 postInvalidate() 的区别
> * Requestlayout，onlayout，onDraw，DrawChild区别与联系
> * LinearLayout、FrameLayout 和 RelativeLayout 哪个效率高
> * LinearLayout的绘制流程
> * 自定义 View 的流程和注意事项
> * 自定义View如何考虑机型适配
> * 自定义控件优化方案
> * invalidate怎么局部刷新
View加载流程（setContentView）

###  事件拦截分发

> > + <img src="./pic/android-事件分发.png" width="1200"/>
> > + 事件分发已经不是直接让你讲了
> > + 关于ACTION_CANCEL
> > > 父布局拦截事件，子view会收到ACTION_CANCEL,首次点击ACTION_DOWN时清空action标识
> > + 例子分析：会给你具体的场景，比如 A 嵌套 B ，B 嵌套 C，从 C 中心按下，一下滑出到 A，事件分发的过程，这里面肯定会有 ACTION_CANCEL 的相关调用时机。
> > > 滑动出C范围到A的范围时不会触发ACTION_CANCEL，抬起时不会触发click事件，onTouchEvent方法再触发performClick前会判断是否有pressed标识位，ACTION_MOVE移动出该视图范围时会清除该标识位
### ANR

### 内存泄漏


### OOM




## Kotlin
[kotlin相关必知必会](https://gonglipeng.blog.csdn.net/article/details/124001671?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7EPayColumn-1-124001671-blog-122735114.235%5Ev38%5Epc_relevant_anti_t3_base&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7EPayColumn-1-124001671-blog-122735114.235%5Ev38%5Epc_relevant_anti_t3_base&utm_relevant_index=1)




