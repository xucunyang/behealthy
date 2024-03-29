## Java基础知识


### 内存模型
> + 理解Java内存模型   https://juejin.im/post/5bf2977751882505d840321d
#### 内存结构
> + <img alt="内存结构" src="./screnncapure/JVM内存结构.png" width="900">
> > [参考 - JVM 学习笔记（一）内存结构](https://blog.csdn.net/m0_45861545/article/details/120692847)
> > 1. 方法区 - Method Area（堆内存）
> > > + java文件编译成.class文件后通过ClassLoader加载到虚拟机中，将类的方法、代码、参数等保存到方法区;
> > > + 内存可以不连续；
> > > + 线程间共享；没有垃圾回收
> > 2. 堆内存 - Heap
> > > + new出来的对象实例存放到堆内存中；
> > > + 有垃圾回收机制；堆内存溢出错误
> > 3. 程序计数器 - PC Register（栈内存）
> > > + 解释器会将指令解释成机器码给到cpu执行，程序计数器会将要执行的下一条指令的地址行号给到解释器供下一次解释执行；
> > > + 记录下一条JVM执行指令的地址行号；
> > > + 线程私有；
> > > +  多线程切换时的上下文指令行号；
> > 4. 虚拟机栈 - JVM Stacks（栈内存）
> > > + 每个线程运行需要的内存空间称为虚拟机栈；
> > > + 由多个栈帧（Stack Frame）组成（局部变量表；操作数栈；动态链接、方法出口等），只有一个活动栈帧，为正在执行的方法，方法的执行过程对应一个栈帧入栈到出栈的过程；
> > > + 每个线程都有各自虚拟机栈和程序计数器;
> > > + 线程私有
> > 5. 本地方法栈 - Native Method Stacks(栈内存)
> > > + 执行native方法时使用的栈

#### JVM内存模型区别（Java 7/8/9内存模型区别）
> + https://blog.csdn.net/laomo_bible/article/details/83067810

#### 垃圾回收算法
> + 判断对象是否需要回收？
>   + 引用计数法
>   + 可达性分析
>  >  + GC Roots
>>> + JVM栈栈帧中（局部变量表）引用的对象 ，native方法栈引用的对象
>>> + 方法区中类静态属性、常量引用的对象
> + JVM 类加载机制、垃圾回收算法对比、Java 虚拟机结构
> 
##### 算法
> > + 标记-清除
> > > <img alt="标记-清除" src="./pic/垃圾回收-标记-清除.png" width="500"/>
> > + 标记-整理
> > > <img alt="标记-整理" src="./pic/垃圾回收-标记-整理.png" width="700"/>
> > + 复制
> > > <img alt="标记-复制.." src="./pic/垃圾回收-标记-复制.png" width="700"/>
> > + 分代回收
> > > <img alt="标记-代回收" src="./pic/垃圾回收-分代回收.png" width="900"/>
> + Q:当你讲到分代回收算法的时候，不免会被追问到新生对象是怎么从年轻代到老年代的，以及可以作为 root 结点的对象有哪些
> + A: 堆内存氛围新生代（YoungGen）、幸存区from、幸存区to、老年代（OldGen），比例为(YoungGen : SurvivorFrom : SurvivorTo) : OldGen = (8 : 1 : 1) : 20 通常新创建的对象放在新生代，内存不够时触发Minor GC(标记-复制)，幸存的对象进入Survivor，年龄+1，每熬过一次Minor GC年龄+1，当达到15（可配置）时，进入老年代；老年代采用Major GC（标记整理、标记清除算法）；还存在永久代存放JVM加载的类文件信息、静态变量、常量
>   
> + 谈谈对 JVM 的理解?  
> + JVM 内存区域，开线程影响哪块区域内存？ 
> + 对 Dalvik、ART 虚拟机有什么了解？对比？

#### 类加载过程 （需要多看看，重在理解，对于热修复和插件化比较重要）
> > <img src="./pic/类加载过程.png" width="900"/>

> > 1. 加载：通过classLoader将二进制文件加载到内存；并.class数据存储方法区运行时数据结构；在堆中生成Class对象作为运行时数据结构入口；
> > 2. 链接：
> > > * (1) 验证：验证字节码是否对jvm有危害，语法、逻辑是否正确
> > > * (2) 准备：类变量赋初值（类型默认值）
> > > * (3) 解析：符号引用替换为地址引用，变量、方法名、类名参数等
> > 3. 初始化：执行init()构造方法，变量赋初始值
> > 4. 使用
> > 5. 卸载
> > * 参考链接[JVM 学习笔记（一）内存结构-CodeAli](https://blog.csdn.net/weixin_50280576/article/details/113742011?spm=1001.2014.3001.5502)
> > * 参考链接[JVM 学习笔记（二）垃圾回收-CodeAli](https://blog.csdn.net/weixin_50280576/article/details/113775575?spm=1001.2014.3001.5502)
> > * 参考链接[JVM 学习笔记（三）类加载与字节码技术&内存模型-CodeAli](https://blog.csdn.net/weixin_50280576/article/details/113784268?spm=1001.2014.3001.5502)
> > * 参考链接[java类的加载过程](https://blog.csdn.net/mojir/article/details/103436878)

### 数据结构
> + 数组
> + ArrayList & LinkedList
> +  Map & Set
> + HashMap & HashSet & HashTable
> > * HashMap
> > > 1. 长度总是2^n，为什么需要这样子，默认大小是16：
> > > > * 数组的length - 1为0x0111111形式二进制值，按位与“&“运算后在高16位与低16位异或运算”^“即为该键值对的索引，这样哈希碰撞的概率降低提高查找效率，put/get都需要使用hash计算
> > > > * 当容量达到阈值时扩容需要重新计算各Entry的hash索引，此时长度为2^n作为容量可以保证只有一半的Entry需要调整位置，并能快速得出新位置的索引
> > > 2. 由数组实现，根据key的hash值确定位置，key相同情况以链表形式存储，新put的K/V生成的Entry放在链表结尾
> * [Java中Vector和ArrayList的区别](https://blog.csdn.net/qq_43170213/article/details/89335990)


### 反射



#### HOOK


### 并发

#### 并发的由来
> > * 主内存传输速度慢会拖慢cpu处理速度，故而引入传输速度更快的高速缓存（工作内存）
> > * 工作内存传输速度慢
> > * 工作内存中的处理的是主内存的副本，需要同步存在并发问题
> > > + 原子性（不可分割）
> > > + 可见性（对多核多线程多能取到最新的值）
> > > + 有序性（为提高运行效率会指令重排）
> > >
> > > [Java 并发编程上篇 -（Synchronized 原理、LockSupport 原理、ReentrantLock 原理）](https://blog.csdn.net/weixin_50280576/article/details/113033975?spm=1001.2014.3001.5502)
> > > [Java 并发编程中篇 -（JMM、CAS 原理、Volatile 原理）](https://blog.csdn.net/weixin_50280576/article/details/113532093)
> > > [Java 并发编程下篇 -（线程池）](https://blog.csdn.net/weixin_50280576/article/details/113532107)
> > > [Java 并发编程下篇 -（JUC、AQS 源码、ReentrantLock 源码）](https://blog.csdn.net/weixin_50280576/article/details/113727645)

#### synchronized 和 volatile关键字

##### 面试QA
> > + Q:volatile为什么不能保证i++
> > > A:不能保证原子性，i++ => temp = i + 1 => i = temp

> > + CAS 乐观锁
> + 乐观锁，compare and swap，UNSAFE#compareAndSwap()
内存值origin，内存值副本copy，修改后的是changed，
cas操作若copy==origin，则origin=changed。
如何保证cas同时别的线程不会cas，处理器cpu层作了限制保证原子性
https://www.cnblogs.com/myopensource/p/8177074.html



#### 线程


##### 线程有哪些状态，哪些锁，各种锁的区别
* NEW（初始化状态）、RUNNABLE（可运行状态/运行状态）、BLOCKED（阻塞状态）、WAITING（等待状态）、TIMED_WAITING（有时限的等待）、TERMINATED（终止状态）
* [理解Java线程状态（6种，6种，6种）](https://blog.csdn.net/acc__essing/article/details/127470780)

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

> * 【问题1】为什么连接的时候是三次握手，关闭的时候却是四次握手？

> > 答：因为当Server端收到Client端的SYN连接请求报文后，可以直接发送SYN+ACK报文。其中ACK报文是用来应答的，SYN报文是用来同步的。所以建立连接只需要三次握手。
但是关闭连接时，当Server端收到FIN报文时，很可能并不会立即关闭SOCKET，所以只能先回复一个ACK报文，告诉Client端，“你发的FIN报文我收到了”。只有等到我Server端所有的报文都发送完了，我才能发送FIN报文，因此不能一起发送。故需要四步握手。

> * 【问题2】为什么TIME_WAIT状态需要经过2MSL(最大报文段生存时间)才能返回到CLOSE状态？

>> 答：虽然按道理，四个报文都发送完毕，我们可以直接进入CLOSE状态了，但是我们必须假象网络是不可靠的，有可以最后一个ACK丢失。所以TIME_WAIT状态就是用来重发可能丢失的ACK报文。在Client发送出最后的ACK回复，但该ACK可能丢失。Server如果没有收到ACK，将不断重复发送FIN片段。所以Client不能立即关闭，它必须确认Server接收到了该ACK。Client会在发送出ACK之后进入到TIME_WAIT状态。Client会设置一个计时器，等待2MSL的时间。如果在该时间内再次收到FIN，那么Client会重发ACK并再次等待2MSL。所谓的2MSL是两倍的MSL(Maximum Segment Lifetime)。MSL指一个片段在网络中最大的存活时间，2MSL就是一个发送和一个回复所需的最大时间。如果直到2MSL，Client都没有再次收到FIN，那么Client推断ACK已经被成功接收，则结束TCP连接。

> * 【问题3】为什么需要三次握手？为什么不能用两次握手进行连接？

>> 答：3次握手完成两个重要的功能，既要双方做好发送数据的准备工作(双方都知道彼此已准备好)，也要允许双方就初始序列号进行协商，这个序列号在握手过程中被发送和确认。

> > 如果使用两次握手就建立连接，就会出现出现以下情况：
> > 我们假设client发出的第一个连接请求报文段并没有丢失，而是在某个网络结点长时间的滞留了，以致延误到连接释放以后的某个时间才到达server。本来这是一个早已失效的报文段。但server收到此失效的连接请求报文段后，就误认为是client再次发出的一个新的连接请求。于是就向client发出确认报文段，同意建立连接。
>假设采用“两次握手”，那么只要server发出确认，新的连接就建立了。由于现在client并没有发出建立连接的请求，因此不会理睬server的确认，也不会向server发送数据。但server却以为新的运输连接已经建立，并一直等待client发来数据。这样，server的很多资源就白白浪费掉了。
>把三次握手改成仅需要两次握手，死锁是可能发生的。作为例子，考虑计算机S和C之间的通信，假定C给S发送一个连接请求分组，S收到了这个分组，并发送了确认应答分组。按照两次握手的协定，S认为连接已经成功地建立了，可以开始发送数据分组。可是，C在S的应答分组在传输中被丢失的情况下（第二次握手丢失），将不知道S是否已准备好，不知道S建立什么样的序列号，C甚至怀疑S是否收到自己的连接请求分组。在这种情况下，C认为连接还未建立成功，将忽略S发来的任何数据分组，只等待连接确认应答分组。而S在发出的分组超时后，重复发送同样的分组。这样就形成了死锁。

> * 【问题4】如果已经建立了连接，但是客户端突然出现故障了怎么办？
>> TCP还设有一个保活计时器，显然，客户端如果出现故障，服务器不能一直等下去，白白浪费资源。服务器每收到一次客户端的请求后都会重新复位这个计时器，时间通常是设置为2小时，若两小时还没有收到客户端的任何数据，服务器就会发送一个探测报文段，以后每隔75秒钟发送一次。若一连发送10个探测报文仍然没反应，服务器就认为客户端出了故障，接着就关闭连接。
>> [参考原文链接](https://blog.csdn.net/m0_45861545/article/details/120692847)


#### TCP / UDP & 应用场景
> <img alt="TCP三次握手与四次挥手" src="./pic/TCP三次握手与四次挥手.png" width="600" />

> + TCP的全称是Transmission Control Protocol，它被称为是一种面向连接的协议，这是因为一个应用程序开始向另一个应用程序发送数据之前，这两个进程必须先进行握手，握手是一个逻辑连接，并不是两个主机之间进行真实的握手。
> > * 面向连接：一定是「一对一」才能连接，不能像UDP协议可以一个主机同时向多个主机发送消息，也就是一对多是无法做到的；
> > * 可靠的：无论的网络链路中出现了怎样的链路变化，TCP都可以保证一个报文一定能够到达接收端；
> > * 字节流：消息是「没有边界」的，所以无论我们消息有多大都可以进行传输。并且消息是「有序的」，当「前一个」消息没有收到的时候，即使它先收到了后面的字节已经 收到，那么也不能扔给应用层去处理，同时对「重复」的报文会自动丢弃。

> + UDP的全称是User Datagram Protocol，它被称为是一种面向无连接的协议，对自己提供的连接实施控制。适用于实时应用，例如：IP电话、视频会议、直播等，以报文的方式传输，效率高，即使知道有破坏的包也不进行重发。
> > * 无连接: 知道对端的IP和端口号就直接进行传输, 不需要建立连接.
> > * 不可靠: 没有确认机制, 没有重传机制; 如果因为网络故障该段无法发到对方, UDP协议层也不会给应用层返回任何错误信息.
> > * 面向数据报: 不能够灵活的控制读写数据的次数和数量.

#### HTTP1.0 & HTTP2.0区别
> + HTTP1.0
> > + 默认使用 Connection:cloose，浏览器每次请求都需要与服务器建立一个 TCP 连接，服务器处理完成后立即断开 TCP 连接（无连接），服务器不跟踪每个客户端也不记录过去的请求（无状态）。
> + HTTP1.1
> > + 默认使用 Connection:keep-alive（长连接），避免了连接建立和释放的开销；通过 Content-Length 字段来判断当前请求的数据是否已经全部接受。不允许同时存在两个并行的响应。
> > * 缺点：
> > > * 高延迟，带来页面加载速度的降低。（网络延迟问题只要由于队头阻塞，导致宽带无法被充分利用）
> > > * 无状态特性，带来巨大的Http头部。
> > > * 明文传输，不安全。
> > > * 不支持服务器推送消息。
> * HTTP2.0 新特性
> > > > 1. 二进制传输 将请求和响应数据分割为更小的帧，并且它们采用二进制编码(http1.0基于文本格式)。多个帧之间可以乱序发送，根据帧首部的流表示可以重新组装。
> > > > 2. Header压缩 开发了专门的“HPACK”算法，大大压缩了Header信息。
> > > > 3. 多路复用 引入了多路复用技术，很好的解决了浏览器限制同一个域名下的请求数量的问题。多路复用技术可以只通过一个TCP链接就可以传输所有的请求数据。
> > > > 4. 服务端推送 在一定程度上改不了传统的“请求-应答”工作模式，服务器不再完全被动地响应请求，也可以新建“流”主动向客户端发送消息。（例如，浏览器在刚请求html的时候就提前把可能会用到的JS，CSS文件发送给客户端，减少等待延迟，这被称为“服务端推送Server Push”）;服务器也不能随便将第三方资源推送给服务器，必须经过双方确认。
> * HTTP2.0缺点
> > > 1. TCP以及TCP+TLS建立连接的延迟（握手延迟）
> >> 2. TCP的队头阻塞没有彻底解决（http2.0中，多个请求是跑在一个TCP管道中的，一旦丢包，TCP就要等待重传（丢失的包等待重新传输确认），从而阻塞该TCP连接中的所有请求）

> * Http3.0
> > Google在推行SPDY的时候意识到了上述http2.0一系列问题，于是又产生了基于UDP协议的“QUIC”协议，让HTTP跑在QUIC上而不是TCP上。从而产生了HTTP3.0版本，它解决了“队头阻塞”的问题。
> 特点：
> （1）实现了类似TCP的流量控制，传输可靠性的功能。
>
> （2）实现了快速握手功能（QUIC基于UDP，UDP是面向无连接的，不需要握手和挥手，比TCP快）
>
> （3）集成了TLS加密功能
>
> （4）多路复用，彻底解决TCP中队头阻塞的问题（单个“流”是有序的，可能会因为丢包而阻塞，但是其他流不会受到影响）
>
> 总结
> HTTP1.1的缺点：安全性不足和性能不高；
> HTTP2.0完全兼容HTTTP1.0，是“更安全的HTTP，更快的HTTPS”，头部压缩，多路复用等技术充分利用了带宽，降低了延迟。
> HTTP3.0的底层支撑协议QUIC基于UDP实现，又含TCP的特点，实现了又快又可靠的协议。

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

> + 代理模式，JAVA动态代理（通过ProxyGenerator生成代理对象的字节码，通过class.forName封装对应方法method实现调用）
> + 代理和委托

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


### 常见框架

> + Glide
> > 1. 生命周期感知，通过activity添加不可见fragment
> > 2. 外观模式隐藏复杂实现
> > 3. 三级缓存，活动缓存、内存缓存、磁盘缓存；LruCache，最近最少使用策略，使用的cache移动到栈顶，栈满了移除栈底元素
> + RxJava
> > > 观察者模式，常用操作符crate,map,flatMap,just,filter
> + OkHttp
> > > 连接池，相同地址的socket复用；拦截器
> + Retrofit
> > 1. 基于okhttp，动态代理+注解
> + CC
> > >
> +动态换肤
> + Jetpack architecture
> > + ViewModel Livedata
> > + Lifecycle
> > + Compose

###  解决过的一些性能问题，在项目中的实际运用

#### 性能优化 

> + 启动优化：
按需初始化：多进程
异步初始化：多线程，线程池
延迟初始化：即仅初始化立即需要用到的对象，不要创建全局静态对象，而是移动到单例模式，在程序第一次使用它的时候进行初始化。
空闲时初始化：IdleHandler

> +  UI优化： 嵌套层级：约束布局
	逻辑分离： 按需加载：viewStub FrameLayout LinearLayout
	应用瘦身： webp

> +  列表优化：
> > 1. 数据处理和视图加载分离：数据的处理逻辑尽可能放在异步处理，onBindViewHolder 方 法中只处理数据填充到视图中。
> > 2. 数据优化：分页拉取远端数据，对拉取下来的远端数据进行缓存，提升二次加载速度；对 于新增或者删除数据通过 DiffUtil 来进行局部刷新数据，而不是一味地全局刷新数据。
> > 3. 布局优化：减少布局层级，简化 ItemView
> > 4. 升级 RecycleView 版本到 25.1.0 及以上使用 Prefetch 功能
> > 5. 通过重写 RecyclerView.onViewRecycled(holder) 来回收资源
> > 6. 如果 Item 高度是固定的话，可以使用 RecyclerView.setHasFixedSize(true); 来避免 requestLayout 浪费资源
> > 7. 对 ItemView 设置监听器，不要对每个 Item 都调用 addXxListener，应该大家公用一个 XxListener，根据 ID 来进行不同的操作，优化了对象的频繁创建带来的资源消耗
> > 8. 如果多个 RecycledView 的 Adapter 是一样的，比如嵌套的 RecyclerView 中存在一样 的 Adapter，可以通过设置 RecyclerView.setRecycledViewPool(pool)，来共用一个 RecycledViewPool。

> + 图片优化：
	改变尺寸： 采样率 matrix压缩
	改变质量： option

> + 工具
(TraceView、Systrace、调试 GPU 过度绘制 & GPU 呈现模式分析、Hierarchy Viewer、MAT、Memory Monitor & Heap Viewer & Allocation Tracker 等）

> + 调优  （讲讲你自己项目中做过的性能优化）

>> 1. 网络：API 优化、流量优化、弱网优化  
> > 2. 内存：OOM 处理、内存泄漏、内存检测、分析、Bitmap 优化 ,LeakCanary 原理，什么检测内存泄漏需要两次？
> > 3. 绘制
> > 4. 电量：WeakLock 机制、JobScheduler 机制
> > 5. APK 瘦身 （APK 瘦身是怎么做的，只用 armabi-v7a 没有什么问题么？
> >     APK 瘦身这个基本是 100% 被面试问到，可能是我简历上提到的原因。）
> > 6. 内存抖动
> > 7. 内存泄漏
> > 8. 卡顿 {如何检测卡顿，卡顿原理是什么，怎么判断页面响应卡顿还是逻辑处理造成的卡顿} ，BlockCanary 的原理
> > 9. 布局优化、过度渲染处理、ANR 处理、监控、埋点、Crash 上传。
> > 10. 启动优化

> > Glide ：加载、缓存、LRU 算法  
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
[kotlin相关必知必会](https://gonglipeng.blog.csdn.net/article/details/124001671?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7EPayColumn-1-124001671-blog-122735114.235%5Ev38%5Epc_relevant_anti_t3_base&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7EPayColumn-1-124001671-blog-122735114.235%5Ev38%5Epc_relevant_anti_t3_base&utm_relevant_index=1)

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


泛型
注解
lambda表达式

RePlugin

rx
just 输入参数
map 对于每个just的item参数 进行处理

subscribeOn 指定事件发射源说在的线程环境

observeOn	观察所在的线程 Android中一般 指定AndroidSchedulers.mainThread

flatMap 对于发射源的数据逐个进行处理Observable 合并成Observable集合 接受端可能顺序不一样不保证
concatMap 将数据连续发送保证顺序 解决flatMap的无序问题


Dagger2
解耦复杂对象构造函数

类的加载过程

AbstractQueuedSynchronizers

ListView & RecycleView


长连接&短连接

udp tcp

volatile
	https://www.cnblogs.com/monkeysayhi/p/7654460.html
	主内存 -> 工作内存
	线程操作全局变量时候是拷贝一份副本到局部线程的寄存器中操作的，所以会出现多线程环境操作全局变量出现错乱
volatile关键字告诉线程直接读取全局变量内存中的值，可以实现修改后立即可见；同时，volatile关键字不允许jvm重新排列指令的执行步骤保证线程安全
需要注意的是，当进行包含本变量操作时，不保证线程安全
allocate =


gc回收的root
	系统类加载器加载的类
	虚拟机栈中的对象，栈帧中的本地变量
	方法区的静态属性引用的对象
	方法区常量引用的对象
	jni本地方法的引用的对象

ArrayList & Vector
	ArrayList线程不安全，Vector线程安全
ArrayList & LinkedList
	 支持随机访问,查询速度快。但是插入和删除都需要移动元素，比起链表开销较大.
	 插入删除效率高，查询不支持随机访问稍微慢

copyonWrite 实现原理

对多写少的情况

CopyOnWriteList

长连接和短连接的区别


 public final <R> Observable<R> map(Function<? super T, ? extends R> mapper) {
        //第一层
        return create(new ObservableOnSubscribe<R>() { //新建了一个事件源

            @Override
            public void call(@NonNull Observer<Object> e) {
                //第二层
                subscribe(new Observer<T>() { //新建了一个订阅者，并且与新建的事件源建立了订阅关系
                   
                    @Override
                    public void onNext(T var1) {
                       //第三层
                        e.onNext(mapper.call(var1));
                    }
                });
            }
        });
    }

// 元注解@Retention(RetentionPolicy.RUNTIME)的作用：说明 注解Carson_Annotation的生命周期 = 保留到程序运行时 & 被加载到 JVM 中
@Retention(RetentionPolicy.RUNTIME)
public @interface Carson_Annotation {
}

<-- 元注解@Retention参数说明 -->
// RetentionPolicy.RUNTIME：注解保留到程序运行时 & 会被加载进入到 JVM 中，所以在程序运行时可以获取到它们
// RetentionPolicy.CLASS：注解只被保留到编译进行时 & 不会被加载到 JVM
// RetentionPolicy.SOURCE：注解只在源码阶段保留 & 在编译器进行编译时将被丢弃忽视。

kotlin apply also let

字节码插桩

socket

线程同步

UI
	ppi原理

		160ppi  1英寸显示160px   1dp = 1px

		326ppi  10dp  

		330ppi  10dp = (330 / 160) * 10 = 20.6
		
	适配

MAT
	systrace使用