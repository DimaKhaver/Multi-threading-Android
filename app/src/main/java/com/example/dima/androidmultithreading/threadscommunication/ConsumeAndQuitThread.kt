package com.example.dima.androidmultithreading.threadscommunication

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.MessageQueue

/**
 * Created by Dima on 1/30/2018.
 */
public class ConsumeAndQuitThread : Thread, MessageQueue.IdleHandler {

    companion object {
        val THREAD_NAME : String = "ConsumeAndQuitThread"
    }

    public var consumerHandler: Handler? = null
    private var isFirstIdle: Boolean = true

    constructor() : super(THREAD_NAME)

    override fun run() {
        Looper.prepare()
        consumerHandler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
            }
        }
      //1. Register the IdleHandler on the background thread when it is started and the
      // Looper is prepared so that the MessageQueue is set up.
        Looper.myQueue().addIdleHandler(this) //
        Looper.loop()
    }

    override fun queueIdle(): Boolean {
      // Let the first queueIdle invocation pass, since it occurs before the first message is received.
        if (isFirstIdle) {
            isFirstIdle = false
            return true //Return true on the first invocation so that the IdleHandler still is registered.
        }
        consumerHandler?.looper?.quit() // Terminate the thread.
        return false
    }

    public fun enqueueData(i : Int) {
        consumerHandler?.sendEmptyMessage(i)
    }
}