package com.example.dima.androidmultithreading.asynchronous_techniques.handler_thread

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.*


/**
HandlerThread offers great control over the Message instances in the queue and
opportunities to observe their state. These features can be used to optionally insert new
tasks in the queue, depending on the pending tasks in the queue when the message is
sent.
HandlerThread provides a single-threaded, sequential task executor with fine-grained
message control. It is the most fundamental form of message passing to a background
thread, and it can be kept alive during a component lifecycle to provide low-resource
background execution. The flexibility of message passing makes the HandlerThread a
strong candidate for customizable sequential executors.
*/
class ChainedNetworkActivity : Activity() {
    companion object {
        private val DIALOG_LOADING = 0
        private val SHOW_LOADING = 1
        private val DISMISS_LOADING = 2
    }

    private var thread: NetworkHandlerThread? = null

  // DialogHandler that processes messages on the UI thread.
  // It is used to control the dialogs shown to the user
    @SuppressLint("HandlerLeak")
    val dialogHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what) {
                SHOW_LOADING -> showDialog(DIALOG_LOADING)
                DISMISS_LOADING -> dismissDialog(DIALOG_LOADING)
            }
        }
    }

    private class NetworkHandlerThread : HandlerThread {
        companion object {
            private val STATE_A = 1
            private val STATE_B = 2
        }

        private var handler: Handler? = null

        constructor() : super("NetworkHandlerThread", 2) {}

        override fun onLooperPrepared() {
            super.onLooperPrepared()
            handler = object : Handler(looper) {
                override fun handleMessage(msg: Message?) {
                    super.handleMessage(msg);
/*
                    The first network call, which is initiated in the onCreate method. It passes a
                    message to the UI thread that initiates a progress dialog. When the network
                    operation is done, the successful result is either passed on to the second task—
                    STATE_B — or the progress dialog is dismissed.

                      case STATE_A:
                      dialogHandler.sendEmptyMessage(SHOW_LOADING);
                      String result = networkOperation1();
                      if (result != null) {
                        sendMessage(obtainMessage(STATE_B, result));
                      } else {
                        dialogHandler.sendEmptyMessage(DISMISS_LOADING);
                      }
                      break;

                      // Execution of the second network operation
                      case STATE_B:
                        networkOperation2((String) msg.obj);
                        dialogHandler.sendEmptyMessage(DISMISS_LOADING);
                      break;
*/
                }
            }
            fetchDataFromNetwork() // Initiate a network call when the background thread is started
        }

        private fun networkOperation1(): String {
            SystemClock.sleep(2000) // Dummy
            return "A string"
        }

        private fun networkOperation2(data: String) {
            // Pass data to network - with HttpPost.
            SystemClock.sleep(2000) // Dummy
        }

      //Publically exposed network operation
        fun fetchDataFromNetwork() {
            handler?.sendEmptyMessage(STATE_A)
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thread = NetworkHandlerThread()
        thread!!.start()
    }

    override fun onCreateDialog(id: Int): Dialog? {
        var dialog: Dialog? = null
        when (id) {
            DIALOG_LOADING -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Loading...")
                dialog = builder.create()
            }
        }
        return dialog
    }

    /**
     * Ensure that the background thread is terminated with the Activity.
     */
    override fun onDestroy() {
        super.onDestroy()
        thread?.quit()
    }
}
