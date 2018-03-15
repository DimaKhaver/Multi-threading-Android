package com.example.dima.multithreadinginandroidandjava.androidmultithreading.asynchronoustechniques.IntentServices

import android.app.IntentService
import android.content.Intent

/**
The IntentService is suitable for when you want to offload tasks easily from the UI
thread to a background thread with sequential task processing, giving the task a component
that is always active in order to raise the process rank.
IntentService has the advantage of running as an independent component

Service may be preferred:

Control by clients
When you want the lifecycle of the component to be controlled by other components,
choose a user-controlled Service. This goes for both started and bound services.

Concurrent task execution
To execute tasks concurrently, starting multiple threads in Service .

Sequential and rearrangeable tasks
Tasks can be prioritized so that the task queue can be bypassed. For example, a music service
that is controlled by buttons—play, pause, rewind, fast forward, stop, etc—would typically
prioritize a stop request so that it is executed prior to any other tasks in the queue.
*/
class WebService : IntentService {
    companion object {
        private val TAG = (WebService::class as Any).javaClass.name
        public val GET = 1
        public val POST = 2
        public val INTENT_KEY_REQUEST_TYPE = "com.eat.INTENT_KEY_REQUEST_TYPE"
        public val INTENT_KEY_JSON = "com.eat.INTENT_KEY_JSON"
        public val INTENT_KEY_RECIEVER = "com.eat.INTENT_KEY_RECEIVER"
        public val BUNDLE_KEY_REQUEST_RESULT = "com.eat.BUNDLE_KEY_REQUEST_RESULT"
    }

    constructor() : super(TAG) {}

    override fun onHandleIntent(intent: Intent?) {
        var uri = intent?.data
        var requestType = intent?.getIntExtra(INTENT_KEY_REQUEST_TYPE, 0)
        var json = intent?.getSerializableExtra(INTENT_KEY_JSON)
        var reciever = intent?.getParcelableArrayExtra(INTENT_KEY_RECIEVER)

        // TODO: not finished
    }
}