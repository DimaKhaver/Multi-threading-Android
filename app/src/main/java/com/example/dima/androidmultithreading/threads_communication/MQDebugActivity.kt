package com.example.dima.androidmultithreading.threads_communication

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.util.LogPrinter
import android.view.View


/**
 * Created by Dima on 2/9/2018.
 */
class MQDebugActivity : Activity() {
    private val TAG = "EAT"
    private var workerHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val thread = object : Thread() {
            override fun run() {
                Looper.prepare()

                @SuppressLint("HandlerLeak")
                workerHandler = object : Handler() {
                    override fun handleMessage(msg: Message?) {
                        super.handleMessage(msg)
                        Log.d(TAG, "handleMessage - what = " + msg!!.what);
                    }
                }
                Looper.loop()
            }
        }
        thread.start()
    }

    public fun onClick(v: View) {
        workerHandler!!.sendEmptyMessageDelayed(1, 2000)
        workerHandler!!.sendEmptyMessage(2)
        workerHandler!!.obtainMessage(3, 0, 0, Object())!!.target
        workerHandler!!.postDelayed(object: Runnable {
            override fun run() {
                Log.d(TAG, "Execute");
            }
        }, 400)
        workerHandler!!.sendEmptyMessage(5)
        workerHandler!!.dump(LogPrinter(Log.DEBUG, TAG), "")
    }


}