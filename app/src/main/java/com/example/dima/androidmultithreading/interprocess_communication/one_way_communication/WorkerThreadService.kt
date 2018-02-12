package com.example.dima.androidmultithreading.interprocess_communication.one_way_communication

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.*


/*
a Service executes in the server process and communicates
with an Activity in the client process. Hence, the Service implements a Messenger
and passes it to the Activity , which in return can pass Message objects to the Ser
vice . First let’s look into the Service
*/
class WorkerThreadService : Service() {
    var workerThread: WorkerThread? = null
    var workerMessenger: Messenger? = null

    override fun onCreate() {
        super.onCreate()
      //1. The messages are handled on a worker thread, which is started upon Service
      //   creation. All binding clients will use the same worker thread.
        workerThread?.start()
    }

    // Worker thread has prepared a looper and handler.
    public fun onWorkerPrepared() {
      //2. A Handler to the worker thread is connected to the Messenger upon
      //construction. This Handler will process incoming messages from client processes.
        workerMessenger = Messenger(workerThread?.workerHandler)
    }

  //3. A binding client receives the IBinder object of the Messenger, so that
  //   the client can communicate with the associated Handler in the Service
    override fun onBind(intent: Intent): IBinder? {
        return workerMessenger?.getBinder()
    }

    override fun onDestroy() {
        super.onDestroy()
        workerThread?.quit()
    }

    class WorkerThread : Thread() {
        var workerHandler: Handler? = null

        override fun run() {
            super.run()
            Looper.prepare()

            @SuppressLint("HandlerLeak")
            workerHandler = object : Handler() {
                override fun handleMessage(msg: Message?) { // Process incoming messages.
                    super.handleMessage(msg)
                }
            }
          //onWorkerPrepared()
          //Looper.loop()
        }

        fun quit() {
            workerHandler?.getLooper()?.quit()
        }
    }
}


