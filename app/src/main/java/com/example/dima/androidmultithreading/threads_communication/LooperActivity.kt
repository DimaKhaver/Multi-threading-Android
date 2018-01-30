package com.example.dima.androidmultithreading.threads_communication

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View

/**
 * Created by Dima on 1/30/2018.
 */
private class LooperActivity : Activity {
    var looperThread: LooperThread? = null

    public constructor()

    // 1. Definition of the worker thread, acting as a consumer of the message queue.
    class LooperThread : Thread {
        var handler: Handler? = null

        public constructor()

        override fun run() {
            Looper.prepare() // 2. Associate a Looper & implicitly a MessageQueue with the thread.
/*
3. Set up a Handler to be used by the producer for inserting messages in the queue.
Here we use the default constructor so it will bind to the Looper of the current
thread. Hence, this Handler can created only after Looper.prepare() , or it will
have nothing to bind to.
*/
            handler = @SuppressLint("HandlerLeak")
            object : Handler() {
                //4. Callback that runs when the message has been dispatched to the worker thread.
                //It checks the what parameter and then executes the long operation.
                override fun handleMessage(msg: Message) {
                    if(msg.what == 0) {
                        //doLongRunningOperation()
                    }
                }
            }
            // 5. Start dispatching messages from the message queue to the consumer thread.
            // This is a blocking call, so the worker thread will not finish
            Looper.loop()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //6. Start the worker thread, so that it is ready to process messages.
        looperThread = LooperThread()
        looperThread?.start()
    }

    public fun onClick(v: View) {
    // 7. Initialize a Message -object with the what argument arbitrarily set to 0
        var msg : Message? = looperThread?.handler?.obtainMessage(0)
    // 8. Insert the message in the queue.
        looperThread?.handler?.sendMessage(msg)
    }

    private fun doLongRunningOperation() {
        // Add long running operation here.
    }

    protected fun onDestroyIt() {
/*
9. Terminate the background thread. The call to Looper.quit() stops the
dispatching of messages and releases Looper.loop() from blocking so the run
method can finish, leading to the termination of the thread.
*/
        looperThread?.handler?.getLooper()?.quit()
    }
}
