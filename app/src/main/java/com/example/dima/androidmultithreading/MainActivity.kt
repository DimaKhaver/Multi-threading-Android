package com.example.dima.androidmultithreading

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    private fun consumerProducerExample() {
        val cp: ConsumerProducer = ConsumerProducer()
        var thread1: Thread? = null
        var thread2: Thread? = null

        thread1 = Thread(Runnable {
            try {
                cp.produce()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        })
        thread1?.start()

        thread2 = Thread(Runnable {
            try {
                cp.consume()
            } catch (e: InterruptedException) {
                e.printStackTrace();
            }
        })
        thread2?.start()
    }
}
