package com.example.dima.androidmultithreading.memory_management

/*
Inner class implicitly has a reference to the outer class. Threads defined as inner classes
keep references to the outer class, which will never be marked for garbage collection as
long as the thread is executing.

So any objects in the Outer class must be left in memory,
along with objects in the inner SampleThread class, as long as that thread is running.

Threads defined as local classes and anonymous inner classes have the same relations
to the outer class as inner classes, keeping the outer class reachable from a GC root
during execution
*/
class Outer() {
    public fun sampleMethod() {
        val sampleThread = SampleThread()
    }

    private class SampleThread : Thread() {
        override fun run() {
            super.run()
            val sampleObject = Object()
        }
    }
}
/*
Static inner classes are members of the class instance of the enclosing object. Threads
defined in a static inner class therefore keep references to the class of the outer object,
but not to the outer object itself.
Therefore, the outer object CAN BE Garbage Collected once other references to it disappear.
*/
class Outer2() {
    public fun sampleMethod() {
        val sampleThread = SampleThread()
        sampleThread.start()
    }
    companion object {
        private class SampleThread : Thread() {
            override fun run() {
                super.run()
                val sampleObject = Object()
            }
        }
    }
}
/*
Separate the execution environment Thread) from the task Runnable).
If you create a new Runnable as an inner class, it will hold a reference to the
outer class during the execution, even if it is run by a static inner class.
*/
class Outer3 {
    public fun sampleMethod() {
        val sampleThread = SampleThread(Runnable { val sampleObject = Object() })
    }

    companion object {
        public class SampleThread : Thread {
            constructor(runnable: Runnable): super(runnable)
        }
    }
}
