package com.example.dima.androidmultithreading.memorymanagement

import java.lang.ref.WeakReference

/*
Once a Message object is added to the message queue, the Message is
indirectly referenced from the consumer thread. The longer the Mes
sage is pending, is in the queue, or does a lengthy execution on the
receiving thread, the higher the risk is for a memory leak.

Avoid memory leaks:
1. Use Static Inner Classes
2. Use Weak References
3. Stop Worker Thread Execution
4. Retain Worker Threads
5. Clean Up the Message Queue:
    removeCallbacks(Runnable r)
    removeCallbacks(Runnable r, Object token)
    removeCallbacksAndMessages(Object token)
    removeMessages(int what)
    removeMessages(int what, Object object)
*/

public class ExampleOuter {
    private var field: Int? = null

    companion object {
        private class SampleThread : Thread {
            private var outer: WeakReference<ExampleOuter>? = null

            constructor(exampleOuter: ExampleOuter) {
                outer = WeakReference(exampleOuter)
            }

            override fun run() {
                // Do execution and update outer class instance fields.
                // Check for null as the outer instance may have been GC'd.
                if (outer?.get() != null)
                    outer?.get()?.field = 1

            }
        }
    }
}
