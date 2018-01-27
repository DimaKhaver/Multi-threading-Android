package com.example.dima.androidmultithreading;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Dima on 1/22/2018.
 */

/*
Locking mechanisms in Android include:
• Object intrinsic lock
— The synchronized keyword
• Explicit locks
— java.util.concurrent.locks.ReentrantLock
— java.util.concurrent.locks.ReentrantReadWriteLock
-----------
synchronized: helps to create an atomic region in the code
If a thread executes in an atomic region, other threads will be blocked
until no other thread executes in the atomic region.
*/
public class Draft {
    private Draft() {
    }

    public int sharedRes = 0; //sharedResource is exposed to a race condition

    private void raceConditions() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                  //wait();
                    sharedRes++;
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    sharedRes--;
                }
            }
        });
        thread2.start();
    }

    private class SynchronizeAccess {
        private SynchronizeAccess(){

        }
/*
        // Using the intrinsic lock:
        // method level that operates on the intrinsic lock of the enclosing object instance:
        synchronized void changeState1() {
            sharedRes++;
        }
        // block-level that operates on the intrinsic lock of the enclosing object instance:
        void changeState2() {
            synchronized(this) {
                sharedRes++;
            }
        }
        // block-level with other objects intrinsic lock:
        private final Object mLock = new Object();
        void changeState3() {
            synchronized(mLock) {
                sharedRes++;
            }
        }
        // method-level that operates on the intrinsic lock of the enclosing class instance:
        synchronized static void changeState4() {
            //STATICSharedResource++;
        }
        // block-level that operates on the intrinsic lock of the enclosing class instance
        static void changeState5() {
            synchronized(this.getClass()) {
                //STATICSharedResource++;
            }
        }
*/
      // Using explicit locking mechanisms:
        private ReentrantLock mLock = new ReentrantLock();
        public void changeState1() {
            mLock.lock();
            try {
                sharedRes++;
            }
            finally {
                mLock.unlock();
            }
        }

        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        public void changeState2() {
            lock.writeLock().lock();
            try {
                sharedRes++;
            } finally {
                lock.writeLock().unlock();
            }
        }

        private int readState() {
            lock.readLock().lock();
            try {
                return sharedRes;
            } finally {
                lock.readLock().unlock();
            }
        }

    }

    // To start a new custom thread:
    // either implement Runnable or extend Thread
    class MyTask1 implements Runnable {
        @Override
        public void run() {

        }
    }
    class MyTask2 extends Thread {

    }

  //java.lang.Thread -> setPriority(int priority); //based on Java priority: 0(least prioritized)->10
/*
    android.os.Process
    Process.setThreadPriority(int priority); // Calling thread.
    Process.setThreadPriority(int threadId, int priority); // Thread with specific id.
*/
}
