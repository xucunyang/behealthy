package com.yang.me.healthy.test

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface Base {
    fun print();
}

class BaseImpl(val num: Int) : Base {
    override fun print() {
        println("num ->> $num")
    }
}

class Derived(base: Base) : Base by base


//val myName by object: ReadOnlyProperty<Any?, String> {
//    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
//        return "my name xu cun yang"
//    }
//}

//var yourName by object: ReadWriteProperty<Any?, String> {
//    private var _yourN = "default your name"
//    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
//        println("get your name getValue")
//        return _yourN
//    }
//
//    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
//        println("set your name setValue")
//        this._yourN = value
//    }
//}

fun main(args: Array<String>) {
    val b = BaseImpl(12)
    Derived(b).print()

    println("=========================")

    var example = Example()
    println("--> ${example.prop}")

    example.prop = "胥"
    println("--> ${example.prop}")

    println("=========================")

    val lazyString: String by lazy {
        println("lazy init")
        "hello world~~"
    }
    println("lazy $lazyString")

    println("~~lazy $lazyString")
    println("=========================")

    var user = User()
    user.name = "小明----"
    user.name = "小画----"

    println("=========================")

    val map = mutableMapOf(
        "n1ame" to "Bear"
    )
    val goods = Goods(map)
    println("goods -> ${goods.name}")

//    println("=========================")
//    println("myName -> $myName")
//    println("yourName -> $yourName")
//    yourName = "yxy"
//    println("yourName -> $yourName")
    println("=========================")
}

class Goods(val map: Map<String, Any?>) {
    val name: String by map
}


class User {
    var name: String by Delegates.observable("初始值") { prop, old, new ->
        println("旧值：$old -> 新值：$new")
    }
}

class Example {
    var prop: String by Delegate()
}

class Delegate {
    private var _realValue: String = "xu"

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("get value: $thisRef, prop: $property ")
        return _realValue
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("set value: ref: $thisRef, prop: $property, value: $value ")
        this._realValue = value
    }
}