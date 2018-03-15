package com.example.dima.multithreadinginandroidandjava.androidmultithreading.asynchronoustechniques.Services


/*
A bound Service defines a communication interface that the binding components—
referred to as client components—can utilize to invoke methods in the Service . The
communication interface is defined as a set of methods that the Service implements
and executes in the Service process. The client components can bind to a Service
through Context.bindService . Multiple client components can bind to a Service —
and invoke methods in it—simultaneously.
The Service is created when the first binding is set up, and is destroyed when there are
no more client components keeping the binding alive. Internally, the Service keeps a
reference counter on the number of bound components, and when the reference counter
is decremented to zero, the Service is destroyed.


class EatService : Service() {
    override fun onBind(intent: Intent): IBinder { /* Return communication interface */
    }

    override fun onUnbind(intent: Intent): Boolean { /* Last component has unbound */
    }
}

*/