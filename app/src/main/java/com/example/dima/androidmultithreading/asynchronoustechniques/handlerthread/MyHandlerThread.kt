package com.example.dima.androidmultithreading.asynchronoustechniques.handlerthread

import android.os.Handler
import android.os.HandlerThread
import android.os.Message

/**
A Handler can be used to pass any data message or task to the HandlerThread , but the
access to the Handler can be limited by keeping it private in a subclass implementation
— MyHandlerThread , in the following example—and ensuring that the Looper is not
accessible. The subclass defines public methods for clients to use so that the thread itself
defines the communication contract for how it should be accessed.
A terminated HandlerThread can not be reused.

1. Creation: The constructor for HandlerThread takes a mandatory name argument and an optional priority
for the thread:

HandlerThread(String name)
HandlerThread(String name, int priority)

The name argument simplifies debugging, because the thread can be found more
easily in both thread analysis and logging. The priority argument is optional and
should be set with the same Linux thread priority values used in Process.set
ThreadPriority. The default priority is Process.THREAD_PRIORITY_DEFAULT — the same
priority as the UI thread—and can be lowered to Process.THREAD_PRIORITY_BACKGROUND
to execute noncritical tasks.

2. Execution: The HandlerThread is active while it can process messages; as long
as the Looper can dispatch messages to the thread. The dispatch mechanism is set
up when the thread is started through HandlerThread.start and is ready when
either HandlerThread.getLooper returns or on the onLooperPrepared callback. A
HandlerThread is always ready to receive messages when the Handler can be created,
as getLooper blocks until the Looper is prepared.

3. Reset: The message queue can be reset so that no more of the queued messages will
be processed, but the thread remains alive and can process new messages. The reset
will remove all pending messages in the queue, but not affect a message that has
been dispatched and is executing on the thread:

public void resetHandlerThread() {
mHandler.removeCallbacksAndMessages(null);
}

The argument to removeCallbacksAndMessages removes the message with that
specific identifier. null , shown here, removes all the messages in the queue.

4. Termination: A HandlerThread is terminated either with quit or quitSafely ,
which corresponds to the termination of the Looper (“Looper termination” on page
59). With quit , no further messages will be dispatched to the HandlerThread ,
whereas quitSafely ensures that messages that have passed the dispatch barrier
are processed before the thread is terminated. You can also send an interrupt to the
HandlerThread to cancel the currently executing message:

public void stopHandlerThread(HandlerThread handlerThread) {
handlerThread.quit();
handlerThread.interrupt();
}

A terminated HandlerThread instance has reached its final state and cannot be
restarted.
A HandlerThread can also be terminated by sending a finalization task to the Handler
that quits the Looper, and consequently the HandlerThread :

handler.post(new Runnable() {
@Override
public void run() {
Looper.myLooper().quit();
}
});
The finalization task ensures that this will be the last executed task on this thread,
once it has been dispatched by the Looper
 */

class MyHandlerThread : HandlerThread {
    private var handler: Handler? = null

    constructor() : super("MyHandlerThread", 1) {}

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = object : Handler(looper) {
            override fun handleMessage(msg: Message?) {
                when (msg?.what) {
                    1 -> print("")
                }
            }
        }
    }

    public fun publishedMethod1() {
        handler?.sendEmptyMessage(1)
    }

    public fun publishedMethod2() {
        handler?.sendEmptyMessage(2)
    }

}
