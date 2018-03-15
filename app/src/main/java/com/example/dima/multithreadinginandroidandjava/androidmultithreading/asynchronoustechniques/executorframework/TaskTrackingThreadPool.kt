package com.example.dima.multithreadinginandroidandjava.androidmultithreading.asynchronoustechniques.executorframework

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
The ThreadPoolExecutor is commonly used standalone, but it can be extended to let
the program track the executor or its tasks. An application can define the following
methods to add actions taken each time a thread is executed:

void beforeExecute(Thread t, Runnable r)
Executed by the runtime library just before executing a thread

void afterExecute(Runnable r, Throwable t)
Executed by the runtime library after a thread terminates, whether normally or
through an exception

void terminated()
Executed by the runtime library after the thread pool is shut down and there are
no more tasks executing or waiting to be executed
*/
class TaskTrackingThreadPool : ThreadPoolExecutor {
    private var taskCount = AtomicInteger(0)

    constructor() : super(3, 3, 0L, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>()) {}

    override fun beforeExecute(t: Thread?, r: Runnable?) {
        super.beforeExecute(t, r)
        taskCount.getAndIncrement()
    }

    override fun afterExecute(r: Runnable?, t: Throwable?) {
        super.afterExecute(r, t)
        taskCount.getAndDecrement()
    }

    public fun getNbrOfTasks() : Int {
        return taskCount.get()
    }
}