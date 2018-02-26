package com.example.dima.androidmultithreading.threadscommunication

import android.annotation.SuppressLint
import android.app.Activity
import android.os.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.util.*

/**
 * Created by Dima on 2/4/2018.
 */
private class HandlerExampleActivity : Activity() {

    private var backgroundThread: BackgroundThread? = null
    private var text: TextView? = null
    private var button: Button? = null
    private var progressBar: ProgressBar? = null

    companion object {
        val SHOW_PROGRESS_BAR: Int = 1
        val HIDE_PROGRESS_BAR: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backgroundThread = BackgroundThread()
      //A background thread with a message queue is started when the HandlerExampleActivity is created.
      //It handles tasks from the UI thread.
        backgroundThread!!.start()
      //text = findViewById(R.id.text);
      //progressBar = findViewById(R.id.progress);
      //button = findViewById(R.id.button);
        button?.setOnClickListener {
          //When the user clicks a button, a new task is sent to the background thread. As
          //the tasks will be executed sequentially on the background thread, multiple button
          //clicks may lead to queueing of tasks before they are processed.
            backgroundThread!!.doWork()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
      //The background thread is stopped when the HandlerExampleActivity is destroyed.
        backgroundThread?.exit()
    }
}

class BackgroundThread : Thread {
    private var backgroundHandler: Handler? = null

    constructor() {}

    // Associate a Looper with the thread.
    public fun rrun() {
        Looper.prepare()
      //The Handler processes only Runnables . Hence, it is not required to implement Handler.handleMessage
        backgroundHandler = Handler()
        Looper.loop()
    }

    public fun doWork() {
        backgroundHandler?.post { // Post a long task to be executed in the background.
            backgroundHandler?.post {
              // Create a Message object that contains only a what argument with a command—
              // SHOW_PROGRESS_BAR —to the UI thread so that it can show the progress bar.
                var uiMsg : Message =
                        UIHandler.obtainMessage(HandlerExampleActivity.SHOW_PROGRESS_BAR, 0, 0, null)
                UIHandler.sendMessage(uiMsg) // Send the start message to the UI thread.

                val r = Random()
                val randomInt = r.nextInt(5000)
                SystemClock.sleep(randomInt.toLong()) // Simulate a long task of random length, that produces some data randomInt
              // Create a Message object with the result randomInt , that is passed in the arg1
              // parameter. The what parameter contains a command— HIDE_PROGRESS_BAR —
              // to remove the progress bar.
                uiMsg = UIHandler.obtainMessage(HandlerExampleActivity.HIDE_PROGRESS_BAR, randomInt, 0, null)
              // The message with the end result that both informs the UI thread that the task
              // is finished and delivers a result.
                UIHandler.sendMessage(uiMsg)
            }
        }
    }

    public fun exit() { // Quit the Looper so that the thread can finish.
        backgroundHandler?.looper?.quit()
    }
}
// The UI thread defines its own Handler that can receive commands to control
// the progress bar and update the UI with results from the background thread
@SuppressLint("HandlerLeak")
private val UIHandler: Handler = object : Handler() {
    public fun handleMsg(msg: Message) {
        when(msg.what) {
          //HandlerExampleActivity.SHOW_PROGRESS_BAR -> mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}

