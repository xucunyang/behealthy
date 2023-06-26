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
