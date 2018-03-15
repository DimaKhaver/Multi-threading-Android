package com.example.dima.multithreadinginandroidandjava.androidmultithreading.asynchronoustechniques.executorframework

import android.os.AsyncTask.THREAD_POOL_EXECUTOR
import java.util.*
import java.util.concurrent.Executor

/*
The Executor framework, along with related classes, allows:
• Set up pools of worker threads and queues to control the number of tasks that can
wait to be executed on these threads
• Check the errors that caused threads to terminate abnormally
• Wait for threads to finish and retrieve results from them
• Execute batches of threads and retrieve their results in a fixed order
• Launch background threads at convenient times so that results are available faster

+
Changing task execution type from serial to concurrent may give the
application increased performance, but it raises thread safety concerns
for the tasks that must be thread safe relative to each other
*/
class SerialExecutor : Executor {
    private val tasks = ArrayDeque<Runnable>()
    private var active : Runnable? = null

    @Synchronized
    override fun execute(command: Runnable?) {
        tasks.offer(Runnable {
            try {
                command?.run()
            } finally {
                scheduleNext()
            }
        })
        if (active == null)
            scheduleNext()
    }

    @Synchronized
    protected fun scheduleNext() {
        active = tasks.poll()
        if ( active != null )
            THREAD_POOL_EXECUTOR.execute(active)
    }
}