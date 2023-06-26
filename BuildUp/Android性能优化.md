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
