package com.example.dima.androidmultithreading.asynchronous_techniques.handler_thread

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.widget.TextView


/**
The sequential execution of Handler
Thread guarantees thread safety, task ordering, and lower resource consumption than
the creation of multiple threads.
 */

public class SharedPreferencesActivity : Activity() {
    public var textValue : TextView? = null

  //Handler to the UI thread, used by the background thread to communicate with the UI thread
    @SuppressLint("HandlerLeak")
    private val UIHandler : Handler = object: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg?.what == 0) {
                val i = msg.obj as Int
                textValue?.setText(i.toString())
            }
        }
    }
  //Background thread that reads and writes values to SharedPreferences
    private class SharedPreferenceThread : HandlerThread {
        private var pref: SharedPreferences? = null
        private var handler: Handler? = null

        companion object {
            private val KEY = "key"
            private val READ = 1
            private val WRITE = 2
        }

        constructor() : super("SharedPrefThread", 2) {
          //pref = getSharedPreferences("LocalPrefs", Context.MODE_PRIVATE)
        }

        override fun onLooperPrepared() {
            super.onLooperPrepared()
            handler = object : Handler(looper) {
                override fun handleMessage(msg: Message?) {
                    when (msg?.what) {
                      //READ -> UIHandler.sendMessage(UIHandler.obtainMessage(0,
                      //        mPrefs.getInt(KEY, 0)))
                      //WRITE ->
                      //var editor = mPrefs.edit();
                      //editor.putInt(KEY, (Integer)msg.obj);
                      //editor.commit();
                    }
                }
            }
        }

        public fun read() {
            handler?.sendEmptyMessage(READ)
        }

        public fun write(i: Int) {
            handler?.sendMessage(Message.obtain(Message.obtain(handler, WRITE, i)))
        }
    }
  //Start background thread when the Activity is created.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //setContentView(R.layout.activity_shared_preferences)
      //mTextValue = findViewById(R.id.text_value) as TextView
      //mThread = SharedPreferenceThread()
      //mThread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
      //mThread.quit() Ensure that the background thread is terminated with the Activity!
    }
}