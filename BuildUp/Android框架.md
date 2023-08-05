### 常见框架



#### LeakCanary

> * 简述
> 1. LeakCanary.install(application);此时使用application进行registerActivityLifecycleCallbacks，从而来监听Activity的何时被destroy。
> 2. 在onActivityDestroyed(Activity activity)的回调中，去检测Activity是否被回收，检测方式如以下步骤。
> 3. 使用一个弱引用WeakReference指向这个activity，并且给这个弱引用指定一个引用队列queue，同时创建一个key来标识该activity。
> 4. 然后将检测的方法ensureGone()投递到空闲消息队列。
> 5. 当空闲消息执行的时候，去检测queue里面是否存在刚刚的弱引用，如果存在，则说明此activity已经被回收，就移除对应的key，没有内存泄漏发生。
> 6. 如果queue里不存在刚刚的弱引用，则手动进行一次gc。
> 7. gc之后再次检测queue里面是否存在刚刚的弱引用，如果不存在，则说明此activity还没有被回收，此时已经发生了内存泄漏，直接dump堆栈信息并打印日志，否则没有发生内存泄漏，流程结束。

> + 关键问题
> 1. 为什么要放入空闲消息里面去执行？
> 因为gc就是发生在系统空闲的时候的，所以当空闲消息被执行的时候，大概率已经执行过一次gc了。
>
> 2. 为什么在空闲消息可以直接检测activity是否被回收？
> 跟问题1一样，空闲消息被执行的时候，大概率已经发生过gc，所以可以检测下gc后activity是否被回收。
>
> 3. 如果没有被回收，应该是已经泄漏了啊，为什么再次执行了一次gc，然后再去检测？
> 根据问题2，空闲消息被执行的时候，大概率已经发生过gc，但是也可能还没发生gc，那么此时activity没有被回收是正常的，所以我们手动再gc一下，确保发生了gc，再去检测activity是否被回收，从而100%的确定是否发生了内存泄漏。

> 4. IdleHandler什么时候执行，触发时机？
> todo
>
 参考来自[一文让你彻底理解LeakCanary的工作原理- 郭霖](https://mp.weixin.qq.com/s?__biz=MzA5MzI3NjE2MA==&mid=2650261737&idx=1&sn=58c7d21cb64da2ce7cd56ae3f840bb85&chksm=88633986bf14b09070c9afee8da0dfe8f59f3ff361d3a243d7c8b2048e191b5414238a4bcf09&scene=27)
```
// 创建一个引用队列
ReferenceQueue<Object> queue = new ReferenceQueue<>();

private void test() {
    // 创建一个对象
    Object obj = new Object();
    // 创建一个弱引用，并指向这个对象，并且将引用队列传递给弱引用
    WeakReference<Object> reference = new WeakReference(obj, queue);
      // 打印出这个弱引用，为了跟gc之后queue里面的对比证明是同一个
      System.out.println("这个弱引用是:" + reference);
    // gc一次看看(毛用都没)
    System.gc();
    // 打印队列(应该是空)
    printlnQueue("before");

    // 先设置obj为null，obj可以被回收了
    obj = null;
    // 再进行gc，此时obj应该被回收了，那么queue里面应该有这个弱引用了
    System.gc();
    // 再打印队列
    printlnQueue("after");
}

private void printlnQueue(String tag) {
    System.out.print(tag);
    Object obj;
    // 循环打印引用队列
    while ((obj = queue.poll()) != null) {
        System.out.println(": " + obj);
    }
    System.out.println();
}

// 打印结果如下所示：
// 这个弱引用是:java.lang.ref.WeakReference@6e0be858
// before
// after: java.lang.ref.WeakReference@6e0be858

```
[精通Framework是真的可以为所欲为！](https://mp.weixin.qq.com/s?__biz=MzA5MzI3NjE2MA==&mid=2650261619&idx=1&sn=94c3d945594794a82c798db338332e9a&chksm=8863391cbf14b00a7c1c78e905e5b75a035132163138f2564aec2777409bbf81aea94117098a&scene=21#wechat_redirect)



#### Glide
> 1. 生命周期感知，通过activity添加不可见fragment
> 2. 外观模式隐藏复杂实现
> 3. 三级缓存，活动缓存、内存缓存、磁盘缓存；LruCache，最近最少使用策略，使用的cache移动到栈顶，栈满了移除栈底元素
java 哪个类使用这个算法



#### RxJava
> > 观察者模式，常用操作符crate,map,flatMap,just,filter



#### OkHttp
> > 连接池，相同地址的socket复用；拦截器



#### Retrofit
> 1. 基于okhttp，动态代理+注解
> 2. （Retrofit 在 OkHttp 上做了哪些封装？动态代理和静态代理的区别，是怎么实现的）



#### CC
>

#### Jetpack architecture
> + ViewModel Livedata
** Lifecycle **

> > 原理：
> + Compose

> + Kotlin coroutine
    continuation-passing-style + 状态机

> > lifecycleScope

> > viewModelScope

> > flow

> > Glide ：加载、缓存、LRU 算法  
(如何自己设计一个大图加载框架)  
（LRUCache 原理）

2. EventBus


4. ARouter

```
重点：Router 原理，如何实现组件间的通信，组件化平级调用数据的方式。
```

5. 插件化（不同插件化机制原理与流派，优缺点。局限性）


6. 热修复

7. RXJava  
（RxJava 的线程切换原理）



OkHttp


1、OkHttp 源码，网络缓存
2、如果从网络加载一个 10M 的图片，说下注意事项

+动态换肤

### 图片处理
> + 图片优化：
     改变尺寸：
        采样率
        matrix压缩
     改变质量：
        option