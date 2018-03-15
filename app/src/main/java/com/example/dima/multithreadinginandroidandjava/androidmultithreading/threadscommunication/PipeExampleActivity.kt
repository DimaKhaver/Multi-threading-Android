package com.example.dima.multithreadinginandroidandjava.androidmultithreading.threadscommunication

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.example.dima.multithreadinginandroidandjava.R
import java.io.IOException
import java.io.PipedReader
import java.io.PipedWriter

/**
 * Created by Dima on 1/27/2018.
 */

/*
Basic pipe use:
A pipe provides a way for two threads, within the same process,
to connect and establish a one-way data channel. A producer thread writes data to the
pipe, whereas a consumer thread reads data from the pipe.
*/
class PipeExampleActivity : Activity() {
    private val TAG = "PipeExampleActivity" as String
    private var editText: EditText? = null

    public var r: PipedReader? = null
    public var w: PipedWriter? = null

    private var workerThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Set up connection
        r = PipedReader()
        w = PipedWriter()

        try {
            w?.connect(r)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        setContentView(R.layout.activity_pipe)

        editText = findViewById(R.id.edit_text)
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    // Only handle addition of characters
                    if (count > before) {
                        // write the last entered character to the pipe
                        w?.write(s?.subSequence(before, count).toString()) // 3. Transfer tha data
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
      //2. Pass the reader to a processing thread
        workerThread = Thread(TextHandlerTask(r!!))
        workerThread?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        workerThread?.interrupt()
        try {
            r?.close() // 4. Close the connection
            w?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
/*
Whenever a new character is added in the EditText,
the character will be written to the pipe and read in the TextHandlerTask.
The consumer task is an infinite loop that reads a character from the pipe as soon
as there is anything to read. The inner while-loop will block when calling read() if the pipe is empty.
*/
class TextHandlerTask : Runnable {
    private lateinit var reader: PipedReader

    public constructor(reader: PipedReader) {
        this.reader = reader
    }

    override fun run() {
        while(Thread.currentThread().isInterrupted) {
            try {
                var i: Int? = null
                while ({i = reader.read(); i}() != -1) {
                    //ADD TEXT PROCESSING LOGIC HERE
                    Log.d(TAG, "char = " + i)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}