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
1. Binder驱动
2. Sever服务端 获取0号引用注册服务端引用
3. Client客户端 获取0号引用并查找返回服务端
4. ServiceManager 在Binder驱动中0号引用
[Android Binder机制(一) Binder的设计和框架](https://wangkuiwu.github.io/2014/09/01/Binder-Introduce/)


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
> > 同步屏障本质发送一个没有target的消息，然后判断是否是异步消息经行处理
> > Traversal流程中先向MessageQueue插入了同步屏障消息（MessageQueue.postSyncBarrier()），再发送一个异步消息（msg.setAsynchronous(true)）
> > 简单来说，就是找下一个要执行的消息时，如果发现了同步屏障，则往下找异步消息。所以有同步屏障时，Handler只会处理异步消息。
同步屏障本质上是一个target为空的Message；
消息轮询时，如果发现了同步屏障消息，则只处理异步消息，所以Android通过这种方式保证UI渲染任务优先处理。

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
> * View绘制过程
```
 -> Activity.setContentView 
 -> PhoneWindow.setContentView(layoutId)  
 -> PhoneWindow.installDecor()  
 -> PhoneWindow.mDecorView = new DecorView() // 包含layoutId已经加载到视图中
 ...
 -> ActivityThread.handleResumeActivity
 -> Activity.makeVisible
 -> ViewManager.addView
 -> WindowManagerGlobal.addView
 -> ViewRootImpl.setView
 -> ViewRootImpl.requestLayout()
 -> ViewRootImpl.scheduleTraversals
 -> ViewRootImpl.doTraversals
 -> ViewRootImpl.performTraversals
 -> View.onMeasure
 -> View.onLayout
 -> View.onDraw
```
>> [Carson带你学Android：自定义View绘制准备-DecorView创建](https://www.jianshu.com/p/ac3262d233af)

> * 子View创建MeasureSpec创建规则是什么


> * 自定义View的wrap_content不起作用的原因
> > 在onMeasure()中的getDefaultSize（）的默认实现中，当View的测量模式是AT_MOST或EXACTLY时，  
> > View的大小都会被设置成子View MeasureSpec的specSize。因为AT_MOST对应wrap_content；EXACTLY对应match_parent，  
> > 所以，默认情况下，wrap_content和match_parent是具有相同的效果的。
> > 因为在计算子View MeasureSpec的getChildMeasureSpec()中，子View MeasureSpec在属性被设置为wrap_content或match_parent情况下，  
> > 子View MeasureSpec的specSize被设置成parenSize = 父容器当前剩余空间大小
> > 所以：wrap_content起到了和match_parent相同的作用：等于父容器当前剩余空间大小
> > 参考[Android 自定义View：为什么你设置的wrap_content不起作](https://www.jianshu.com/p/ca118d704b5e)

> * 在Activity中获取某个View的宽高有几种方法
> > View.post原理，向绘制Handler中添加消息，等绘制完成后执行，故能过去宽高
> > > mAttachInfo.mHandler.post，执行会在ViewRootImpl中的doTraversals -> performMeasure -> performLayout -> performDraw - runnable.run
> > >View.getViewTreeObserver.addGlobalLayoutListener，跟View.post时机差不多，都在ViewRootImpl的performTraversals中，时机在onLayout后，onDraw前，比view.post早

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

> * Invalidate/RequestLayout区别
> > requestLayout会直接递归调用父窗口的requestLayout，直到ViewRootImpl,然后触发peformTraversals，
> > 由于mLayoutRequested为true，会导致onMeasure和onLayout被调用。不一定会触发OnDraw
> > requestLayout触发onDraw可能是因为在在layout过程中发现l,t,r,b和以前不一样，那就会触发一次invalidate，  
> > 所以触发了onDraw，也可能是因为别的原因导致mDirty非空（比如在跑动画）
> > view的invalidate不会导致ViewRootImpl的invalidate被调用，而是递归调用父view的invalidateChildInParent，  
> > 直到ViewRootImpl的invalidateChildInParent，然后触发peformTraversals，会导致当前view被重绘，  
> > 由于mLayoutRequested为false，不会导致onMeasure和onLayout被调用，而OnDraw会被调用

> * LinearLayout的绘制流程
> > 存在weight会触发两次measure

> * 自定义 View 的流程和注意事项
> * 自定义View如何考虑机型适配
> * 自定义控件优化方案
>
> * invalidate怎么局部刷新


#### 自定义Viwe


View加载流程（setContentView）



###  事件拦截分发

> > + <img src="./pic/android-事件分发.png" width="1200"/>
> > + 事件分发已经不是直接让你讲了
> > + 关于ACTION_CANCEL
> > > 父布局拦截事件，子view会收到ACTION_CANCEL,首次点击ACTION_DOWN时清空action标识
> > + 例子分析：会给你具体的场景，比如 A 嵌套 B ，B 嵌套 C，从 C 中心按下，一下滑出到 A，事件分发的过程，这里面肯定会有 ACTION_CANCEL 的相关调用时机。
> > > 滑动出C范围到A的范围时不会触发ACTION_CANCEL，抬起时不会触发click事件，onTouchEvent方法再触发performClick前会判断是否有pressed标识位，ACTION_MOVE移动出该视图范围时会清除该标识位



### ANR

> + ANR出现常见原因
> > 1. 主线程频繁进行耗时的IO操作：如数据库读写
> > 2. 多线程操作的死锁，主线程被block；
> > 3. 主线程被Binder 对端block；
> > 4. System Server中WatchDog出现ANR；
> > 5. service binder的连接达到上线无法和和System Server通信
> > 6. 系统资源已耗尽（管道、CPU、IO）

### 内存泄漏

> * 常见引发内存泄露原因主要有：
> > + 集合类
> > > 集合类 添加元素后，仍引用着 集合元素对象，导致该集合元素对象不可被回收，从而导致内存泄漏
```
          // 通过 循环申请Object 对象 & 将申请的对象逐个放入到集合List
          List<Object> objectList = new ArrayList<>();        
          for (int i = 0; i < 10; i++) {
               Object o = new Object();
               objectList.add(o);
               o = null;
          }
          // 虽释放了集合元素引用的本身：o=null）
          // 但集合List 仍然引用该对象，故垃圾回收器GC 依然不可回收该对象
```
> > + Static关键字修饰的成员变量
> > > 单例模式应该引用生命周期等于应用生命周期的对象。如上述实例，应传递Application的Context，因Application的生命周期 = 整个应用的生命周期

储备知识
非静态内部类 / 匿名类 默认持有外部类的引用；而静态内部类则不会

常见情况
3种，分别是：非静态内部类的实例 = 静态、多线程、消息传递机制（Handler）> > > + 非静态内部类 / 匿名类

> > + 资源对象使用后未关闭
> > + 
> [Android 常见内存泄漏总结、避免踩坑、提供解决方案。](https://blog.csdn.net/weixin_44235109/article/details/122029725)



### OOM




## Kotlin

#### when
when支持类型enum， string， number更多 is判断
when后可以不带参数，分支条件可以为任意返回boolean的表达式

### 协程

> [Kotlin协程：协程的基础与使用](https://blog.csdn.net/LeeDuoZuiShuai/article/details/125961337)
> _特点_
> >    1）更加轻量级，占用资源更少。
> >    2）避免“回调地狱”，增加代码可读性。
> >    3）协程的挂起不阻塞线程。

> > **涉及的类**
> > + 协程的上下文
> > > 一个结构介于Set和Map之间的索引集。

> > + 协程的作用域  协程作用域用于管理作用域内协程的生命周期
> > > 当父协程被取消或发生异常时，会自动取消父协程所有的子协程。当子协程取消或发生异常时，在coroutineScope作用域下，会导致父协程取消；而在supervisorScope作用域下，则不会影响父协程。
协程的作用域只对父子协程有效，对子孙协程无效。例如：启动父协程，在supervisorScope作用域内启动子协程。当子协程在启动孙协程时，在不指定为supervisorScope作用域的情况下，默认为coroutineScope作用域。

> > + 协程调度器
> >
> > + 协程的启动模式

> > + 协程的生命周期 一个结构介于Set和Map之间的索引集。

#### 使用
> > > * 创建
> > > > + runBlocking
> > > > > 阻塞线程直到lambda执行完毕，测试用

> > > > + GlobalScope.launch
> > > > > 该方法用于在协程作用域中异步启动一个新的协程，调用该方法不会阻塞线程。

> > > > + GlobalScope.async
> > > > > 该方法用于在协程作用域中中异步启动一个新的协程，调用该方法不会阻塞线程。async方法与launch方法的不同之处在于可以携带返回值。  
> > > > > 调用该方法会返回一个Deferred接口指向的对象，调用该对象可以获取协程执行的结果。同时，Deferred接口继承自Job接口，因此仍然可以操作协程的生命周期。

> > > > + GlobalScope.withContext
> > > > > 该方法用于在当前协程的执行过程中，切换到调度器指定的线程去执行参数block中的代码，并返回一个结果。调用该方法可能会使当前协程挂起，并在方法执行结束时恢复挂起。

> > > > + GlobalScope.produce

> > > * 取消
> > > > cancel

> > > * join _Job#join()_
> > > > 阻塞当前协程直到job
> > > > 当Job出现新建状态时也能启动

> > > * 相互通信
> > > > Channel用于协程间的通信。Channel本质上是一个并发安全的队列，类似BlockingQueue。  
> > > > 在使用时，通过调用同一个Channel对象的send和receive方法实现通信



### 原理

> + 续体传递（Continuation-Pass-Style）
> + 状态机
> >  协程的代码在经过Kotlin编译器处理时，会被优化成状态机模型。每段代码有三个状态：未执行、挂起、已恢复(完成)。  
处于未执行状态的代码可以被执行，执行过程中发生挂起，会进入挂起状态，从挂起中恢复或执行完毕会进入已恢复(完成)状态。  
> > 当多个像这样的代码进行协作时，可以组合出更复杂的状态机。

[kotlin相关必知必会](https://gonglipeng.blog.csdn.net/article/details/124001671?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7EPayColumn-1-124001671-blog-122735114.235%5Ev38%5Epc_relevant_anti_t3_base&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7EPayColumn-1-124001671-blog-122735114.235%5Ev38%5Epc_relevant_anti_t3_base&utm_relevant_index=1)



### Flow
响应式事件流
#### 分类
> + 冷流 - collect有消费才会生成事件

> + 热流 - 没有消费也会生成事件，会丢事件

#### 操作符

> + flowOf
> + asFlow
> + flow{ emit(T) }

> + flowOn
> + zip/combine
> + drop
> + take
> + map
> + flatMapMerge
> + flatMapLastest
> + filter

> + onEach
> + onStart
> + onCompletion

> + catch
> + 
> + launchIn
> + collect


