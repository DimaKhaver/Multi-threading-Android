package com.example.dima.multithreadinginandroidandjava.androidmultithreading.threadscommunication

import java.util.concurrent.LinkedBlockingQueue

/**
 * Created by Dima on 1/30/2018.
 */

/*
The consumer-producer pattern implemented with the LinkedBlockingQueue -
implementation is easily implemented by adding messages to the queue with put(), and
removing them with take(), where put() blocks the caller if the queue is full, and
take() blocks the caller if the queue is empty
*/
class ConsumerProducer {
    private val LIMIT = 10
    private val blockingQueue = LinkedBlockingQueue<Int>(LIMIT)

    @Throws(InterruptedException::class)
    fun produce() {
        var value = 0
        while (true) {
            blockingQueue.put(value++)
        }
    }

    @Throws(InterruptedException::class)
    fun consume() {
        while (true) {
            val value = blockingQueue.take()
        }
    }
}
