package com.example.dima.multithreadinginandroidandjava.androidmultithreading.asynchronoustechniques.executorframework

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 * Thread Pool Uses Cases and Pitfalls
 */
class Draft private constructor() {
  // the alive time does not apply to core pool threads, but allowCoreThreadTimeOut(true)
  // allows the system to also reclaim idle core pool threads
    private fun favoringThreadCreationOverQueuing() {
        val N = Runtime.getRuntime().availableProcessors()
        val executor = ThreadPoolExecutor(N * 2, N * 2,
                60L, TimeUnit.SECONDS,
                LinkedBlockingQueue<Runnable>())
        executor.allowCoreThreadTimeOut(true)
    }
  /*
    A thread pool starts with zero worker threads and creates them when needed. Thread
    creation is triggered when tasks are submitted to the thread pool, but if no tasks are
    submitted, no worker threads are created even though there are tasks in the queue.
    Under those conditions, no tasks will be executed until a submission is done.
  */
    private fun handlingPreloadedTaskQueues() {
        val preloadedQueue = LinkedBlockingQueue<Runnable>()
        val alphabet = arrayOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta")
        for (i in alphabet.indices) {
            val j = i
            preloadedQueue.add(Runnable {
                // Do long running operation that uses "alphabet" variable.
            })
        }
        val executor = ThreadPoolExecutor(
                5, 10, 1, TimeUnit.SECONDS, preloadedQueue)
        executor.prestartAllCoreThreads()
    }
  /*
    The state of the thread pool and queue of waiting threads determine how a
    thread pool can respond to a new task:
    • If the core pool size has not been reached yet, a new thread can be created so the
      task can start immediately.
    • If the core pool size has been reached but the queue has open slots, the task can be
      added to the queue.
    • If the core pool size has been reached and the queue is full, the task must be rejected
  */

    private fun addTaskToThreadPool() {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute(Runnable { /*doLongRunningOperation()*/ })
    }
}