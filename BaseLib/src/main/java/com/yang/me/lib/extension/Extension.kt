package com.yang.me.lib.extension
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

val UI: CoroutineDispatcher = Dispatchers.Main
val IO: CoroutineDispatcher = Dispatchers.IO

fun runOnUI(block: suspend CoroutineScope.() -> Unit): Job =
    GlobalScope.launch(context = UI, block = block)

fun runInBackground(block: suspend CoroutineScope.() -> Unit): Job =
    GlobalScope.async(context = IO, block = block)

fun <T> asyncOnUI(block: suspend CoroutineScope.() -> T): Deferred<T> =
    GlobalScope.async(context = UI, block = block)

fun <T> asyncInBackground(block: suspend CoroutineScope.() -> T): Deferred<T> =
    GlobalScope.async(context = IO, block = block)

fun <T> GlobalScope.asyncWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {

    val deferred = async(context, start, block)

    lifecycleOwner.lifecycle.addObserver(CoroutinesLifecycleObserver(deferred))

    return deferred
}

fun GlobalScope.launchWithLifecycle(
    lifecycleOwner: LifecycleOwner?,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {

    val job = launch(context, start, block)

    lifecycleOwner?.lifecycle?.addObserver(CoroutinesLifecycleObserver(job))
}

fun blockWithTryCatch(
    tryBlock: () -> Unit,
    catchBlock: () -> Unit,
    finalBlock: () -> Unit
) {
    try {
        tryBlock()
    } catch (e: Exception) {
        catchBlock()
    } finally {
        finalBlock()
    }
}

fun <T> launchWrapped(
    lifecycleOwner: LifecycleOwner? = null,
    asyncBlock: suspend CoroutineScope.() -> T,
    uiBlock: ((T) -> Unit)? = null,
    exceptionHandler: ((Exception) -> Unit)? = null,
    finalBlock: (() -> Unit)? = null,
    context: CoroutineContext = UI,
    start: CoroutineStart = CoroutineStart.DEFAULT
) {
    val job: Job = GlobalScope.launch(context, start) {
        try {
            val result = withContext(IO) {
                asyncBlock()
            }
            uiBlock?.invoke(result)
        } catch (e: java.lang.Exception) {
            log("[Exception]: $e")
            e.printStackTrace()
            exceptionHandler?.invoke(e)
        } finally {
            finalBlock?.invoke()
        }
    }
    lifecycleOwner?.lifecycle?.addObserver(CoroutinesLifecycleObserver(job))
}

fun log(msg: String) {
    val tag = "Kt-extension"
    Timber.d("$tag ${Thread.currentThread().name} $msg")
}

object MainHandler : Handler(Looper.getMainLooper())

fun runOnUIThread(uiWork: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        uiWork()
    } else {
        MainHandler.post(uiWork)
    }
}

@SuppressLint("ShowToast")
fun toast(
    context: Context, text: CharSequence?, @ColorInt messageColor: Int = Color.WHITE,
    vararg views: Pair<View, Int> = arrayOf()
) =
    runOnUIThread {
        Toast.makeText(context, text, Toast.LENGTH_SHORT)
            .also {
                if (views.isNotEmpty()) {
                    for (addView in views) {
                        (it.view as LinearLayout).addView(addView.first, addView.second)
                    }
                }
            }
            .show()
    }

fun <T> ViewModel.asyncCall(
    asyncBlock: suspend CoroutineScope.() -> T,
    uiBlock: ((T) -> Unit)? = null,
    errBlock: ((e: Exception) -> Unit)? = null
) {
    viewModelScope.launch(UI) {
        try {
            val result = withContext(IO) {
                asyncBlock()
            }
            uiBlock?.let { it.invoke(result) }
        } catch (e: Exception) {
            e.printStackTrace()
            errBlock?.let { it(e) }
        }
    }
}