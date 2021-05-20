package com.yang.me.lib.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.Job
import timber.log.Timber

class CoroutinesLifecycleObserver(private val job: Job,
                                  private val event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY): LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() = handleEvent(Lifecycle.Event.ON_PAUSE)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() = handleEvent(Lifecycle.Event.ON_STOP)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() = handleEvent(Lifecycle.Event.ON_DESTROY)

    private fun handleEvent(e: Lifecycle.Event) {
        // TODO 当job completed 需要如此处理吗
        if (e == event && !job.isCancelled)
        {
            Timber.d("cancel job")
            job.cancel()
        }
    }

}