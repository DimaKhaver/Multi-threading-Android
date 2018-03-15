package com.example.dima.multithreadinginandroidandjava.androidmultithreading.asynchronoustechniques.executorframework

import android.content.ContentValues.TAG
import android.util.Log
import java.util.concurrent.ThreadFactory

/**
Worker threads are configured through implementations of the ThreadFactory inter‐
face. Thread pools can define properties on the worker threads, such as priority, name,
and exception handler.
Because thread pools often have many threads and they compete with the UI thread for
execution time, it is normally a good idea to assign the worker threads a lower priority
than the UI thread. If the priority is not lowered by a custom ThreadFactory, the worker threads,
by default, get the same priority as the UI thread
*/

class LowPriorityThreadHandler : ThreadFactory {
    companion object {
        private var count = 1
    }

    override fun newThread(r: Runnable?): Thread {
        val t : Thread = Thread(r)
        t.name = "LowPrio" + count++
        t.priority = 4
        t.uncaughtExceptionHandler =
                Thread.UncaughtExceptionHandler { t, e ->
                    Log.d(TAG, "Thread = " + t.name + ", error = " + e?.message) }
        return t
    }
}

// Executors.newFixedThreadPool(10, new LowPriorityThreadFactory());