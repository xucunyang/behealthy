## Java基础知识


### 内存模型
> + 理解Java内存模型   https://juejin.im/post/5bf2977751882505d840321d

#### JVM内存模型（Java 7/8/9内存模型区别）
> + https://blog.csdn.net/laomo_bible/article/details/83067810

#### 垃圾回收算法
> + JVM 类加载机制、垃圾回收算法对比、Java 虚拟机结构
> + 当你讲到分代回收算法的时候，不免会被追问到新生对象是怎么从年轻代到老年代的，以及可以作为 root 结点的对象有哪些两个问题。
> + 谈谈对 JVM 的理解?  
> + JVM 内存区域，开线程影响哪块区域内存？ 
> + 对 Dalvik、ART 虚拟机有什么了解？对比？

#### 类加载过程 （需要多看看，重在理解，对于热修复和插件化比较重要）


### 数据结构
> + 数组
> + ArrayList & LinkedList
> +  Map & Set
> + HashMap & HashSet & HashTable


### 反射

#### HOOK


### 并发

#### synchronized 和 volatile关键字

#### 线程

##### 线程有哪些状态，哪些锁，各种锁的区别

##### sleep 、wait、yield 的区别，wait 的线程如何唤醒它

##### 线程池的使用

##### 参数：
> + 核心线程数coreSize
> + 最大线程数
> > 线程数大于coreSize，queue满了是创建
> + 保活时间
> + 保活时间单位
> > 超过coreSize的其他休息时间超过该值的线程会被销毁
> + 工作队列
> > 已有线程不空闲，且queue也满了，则新建线程，并将最新的任务优先提交给新线程处理
> + 线程创建工厂
> > - 指定线程名称和守护进程
> + 拒绝策略
> > 且所有线程max size仍不空闲，queue也是满的，此时就会触发池的拒绝机制
> > - AbortPlicy 抛异常RejectedExecutionException
> > - CallerRunsPolicy 调用者执行
> > - DiscardPolicy 直接丢掉
> > - DiscardOldestPolicy 丢掉最老的任务


### 网络通信

#### 通信模型及应用协议

#### HTTP、HTTPS、TCP/IP、Socket通信、三次握手四次挥手过程

#### TCP / UDP & 应用场景

#### HTTP1.0 & HTTP2.0区别

#### 常见Q&A：
> + TCP 有哪些状态

> + 三次握手、四次挥手。为啥不是三次不是两次

> + HTTPS 和 HTTP 的区别，HTTPS 2.0 3.0？

> + 浏览器输入一个 URL 按下回车网络传输的流程？

> + 问的深一点的可能涉及到网络架构，每层有什么协议，FTP 相关原理，例：TCP 建立连接后，发包频率是怎么样的？


### JAVA基础其他、常见提问

#### Java 四大引用

> + 强引用、软引用、弱引用、虚引用的区别以及使用场景。

> + 强引用置为 null，会不会被回收？

> + 稍微问的深一些的面试官会和内存泄漏检测原理以及垃圾回收糅杂在一起。

#### Java 的泛型， 和  的区别

## 编程思想

hi


.
### 面向对象


### 面向过程


### 切面

#### 字节码插桩

### 设计模式（六大基本原则、项目中常用的设计模式、手写单例等）

> + 生产者模式和消费者模式的区别？

> + 单例模式双重加锁，为什么这样做？

> + 知道的设计模式有哪些？

> + 项目中常用的设计模式有哪些？

> + 手写生产者、消费者模式。

> + 手写观察者模式代码。

> + 适配器模式、装饰者模式、外观模式的异同？

> + 谈谈对 java 状态机的理解。

> + 谈谈应用更新（灰度、强制更新、分区更新？）



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

### BInder

### 四大组件

> + Activity
> + Service
> + Broadcast Receiver
> + ContentProvider

###  自定义 View* 

###  事件拦截分发

+ 事件分发已经不是直接让你讲了，会给你具体的场景，比如 A 嵌套 B ，B 嵌套 C，从 C 中心按下，一下滑出到 A，事件分发的过程，这里面肯定会有 ACTION_CANCEL 的相关调用时机。

### ANR

### 内存泄漏


### OOM


### 常见框架

> + Glide
> + RxJava
> + OkHttp
> + Retrofit
> + CC
> +动态换肤
> + Jetpack architecture
> > + ViewModel Livedata
> > + Lifecycle
> > + Compose

###  解决过的一些性能问题，在项目中的实际运用

#### 性能优化 

> + 工具
(TraceView、Systrace、调试 GPU 过度绘制 & GPU 呈现模式分析、Hierarchy Viewer、MAT、Memory Monitor & Heap Viewer & Allocation Tracker 等）

> + 调优  （讲讲你自己项目中做过的性能优化）

>> 1. 网络：API 优化、流量优化、弱网优化  
>> 2. 内存：OOM 处理、内存泄漏、内存检测、分析、Bitmap 优化 ,LeakCanary 原理，什么检测内存泄漏需要两次？
>> 3. 绘制 
>> 4. 电量：WeakLock 机制、JobScheduler 机制  
>> 5. APK 瘦身 （APK 瘦身是怎么做的，只用 armabi-v7a 没有什么问题么？
   APK 瘦身这个基本是 100% 被面试问到，可能是我简历上提到的原因。）  
>> 6. 内存抖动  
>> 7. 内存泄漏  
>> 8. 卡顿 {如何检测卡顿，卡顿原理是什么，怎么判断页面响应卡顿还是逻辑处理造成的卡顿} ，BlockCanary 的原理 
>> 9. 布局优化、过度渲染处理、ANR 处理、监控、埋点、Crash 上传。 
>> 10. 启动优化
>> Glide ：**加载、缓存、LRU 算法  
(如何自己设计一个大图加载框架)  
（LRUCache 原理）

2. EventBus

3. LeakCanary

4. ARouter

```
重点：Router 原理，如何实现组件间的通信，组件化平级调用数据的方式。
```

**5. 插件化**（不同插件化机制原理与流派，优缺点。局限性）


**6. 热修复**

7. RXJava  
（RxJava 的线程切换原理）



8. Retrofit  
（Retrofit 在 OkHttp 上做了哪些封装？动态代理和静态代理的区别，是怎么实现的）


OkHttp


1、OkHttp 源码，网络缓存 
2、如果从网络加载一个 10M 的图片，说下注意事项 


### Android插件化


## Kotlin


## JavaScript


## React Native



### 其他Q&A
+ Http[s]请求慢的解决办法（DNS、携带数据、直接访问 IP）


+ 缓存自己如何实现（LRUCache 原理）

+  图形图像相关：OpenGL ES 管线流程、EGL 的认识、Shader 相关
+ SurfaceView、TextureView、GLSurfaceView 区别及使用场景
+ 动画、差值器、估值器（Android中的View动画和属性动画 - 简书、Android 动画 介绍与使用）
+ 属性动画、补间动画、帧动画的区别和使用场景
+ MVC、MVP、MVVM
+ 相互间的区别和各种使用场景，如何选择适合自己的开发架构。
+ Handler、ThreadLocal、AsyncTask、IntentService 原理及应用
+ Handler 机制原理，IdleHandler 什么时候调用。
+ Gradle（Groovy 语法、Gradle 插件开发基础）
+ 热修复、插件化  
+ 谈谈对 ClassLoader 的理解  
+ 双亲委托机制的好处  
+ 自定义 ClassLoader
+ 插件化为什么会出现，如何代码加载，资源加载，代理 Hook）
+ 组件化架构思路  
+ 如何从一个老项目一步步实现组件化，主要问思路，考察架构能力和思考能力。（需要考虑很多，每一步做什么，顺序很重要）    
+ 组件化和模块化的理解与区别？

+ 系统打包流程

+ Android 有哪些存储数据的方式。

+ SharedPrefrence 源码和问题点；
+ sqlite 相关  
> + sqlite 升级，增加字段的语句  

> + 数据库框架对比和源码分析  

> + 数据库优化及数据迁移问题  

> + getWritableDatabase 和 getReadableDatabase 的区别

+ 如何判断一个 APP 在前台还是后台？


###  混合开发  



Hybrid 做过吗？ 例：Android 通过WebView调用 JS 代码  例：JS 通过WebView调用 Android 代码； 

Hybrid 通信原理是什么，有做研究吗？ 

说说你用过的混合开发技术有哪些？各有什么优缺点？




### Android Framework



#### AMS 、PMS



#### Activity 启动流程，App 启动流程



#### Binder 机制（IPC、AIDL 的使用）


#### 讲讲 Linux 上的 IPC 通信，Binder 有什么优势，Android 上有哪些多进程通信机制?

* 项目中遇见了什么多进程场景？ 
* AIDL 是什么？解决了什么问题？ 
* 谈谈对进程共享和线程安全的认知？ 



#### 为什么使用 Parcelable，好处是什么？

#### Android 图像显示相关流程，Vsync 信号等

###  

*5*

 算法与数据结构



\1. 单链表：反转、插入、删除



\2. 双链表：插入、删除



\3. 手写常见排序、归并排序、堆排序



\4. 二叉树前序、中序、后序遍历



\5. 最大 K 问题



\6. 广度、深度优先搜索算法



\7. String 转 int。



核心算法就三行代码，不过临界条件很多，除了判空，还需要注意负数、Integer 的最大最小值边界等；



\8. 如何判断一个单链表有环？



\9. 链表翻转；



\10. 快排；



\11. 100 亿个单词，找出出现频率最高的单词。要求几种方案；



\12. 链表每 k 位逆序；



\13. 镜像二叉树；



\14. 找出一个无序数组中出现超过一半次数的数字；



\15. 计算二叉树的最大深度，要求非递归算法。



\16. String 方式计算加法。

###  

*6*

 项目&HR



\1. 项目开发中遇到的最大的一个难题和挑战，你是如何解决的。（95% 会问到）



\2. 说说你开发最大的优势点（95% 会问到）



\3. 你为什么会离开上家公司



\4. 你的缺点是什么？



\5. 你能给公司带来什么效益？



\6. 你对未来的职业规划？



## 开发工具

Android Studio
GIT
GitLab
Charles
Visual Studio Code





## 项目