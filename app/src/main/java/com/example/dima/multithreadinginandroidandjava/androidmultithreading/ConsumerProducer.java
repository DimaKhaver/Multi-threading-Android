package com.example.dima.multithreadinginandroidandjava.androidmultithreading;

import java.util.LinkedList;

/**
 * Created by Dima on 1/23/2018.
 */

/*
consumer-producer pattern:
one thread that produces data and one thread that consumes the data. The threads
collaborate through a list that is shared between them. When the list is not full, the
producer thread adds items to the list, whereas the consumer thread removes items
while the list is not empty. If the list is full, the producing thread should block, and if
the list is empty, the consuming thread is blocked.
 */
public class ConsumerProducer {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int LIMIT = 10;
    private Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;
        while(true) {
            synchronized (lock) {
                while (list.size() == LIMIT) {
                    lock.wait();
                }
                list.add(value++);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        while(true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    lock.wait();
                }
                int value = list.removeFirst();
                lock.notify();
            }
        }
    }
}
