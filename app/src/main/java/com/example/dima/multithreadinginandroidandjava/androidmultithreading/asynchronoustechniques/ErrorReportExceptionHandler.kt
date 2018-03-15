package com.example.dima.multithreadinginandroidandjava.androidmultithreading.asynchronoustechniques

/**
The Android runtime attaches a process global UncaughtExceptionHandler when an
application is started. Thus, the exception handler is attached to all threads in the application,
and it treats unhandled exceptions equally for all threads: the process is killed.
The default behavior can be overriden either globally for all threads—including the UI
thread—or locally for specific threads. Typically, the overriden behavior should only
add functionality to the default runtime behavior, which is achieved by redirecting the
exception to the runtime handler
*/

// Set new global handler Thread.setDefaultUncaughtExceptionHandler(new ErrorReportExceptionHandler());

class ErrorReportExceptionHandler : Thread.UncaughtExceptionHandler {
    private val defaultHandler: Thread.UncaughtExceptionHandler =
            Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
      //reportErrorToFile(throwable)
        defaultHandler.uncaughtException(thread, throwable)
    }
}