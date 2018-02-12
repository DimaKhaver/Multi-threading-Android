package com.example.dima.androidmultithreading.interprocess_communication.one_way_communication

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.view.View

/*
 On the client side, an Activity binds to the Service in the server process and sends messages
*/

class MessengerOneWayActivity : Activity() {

    private var bound: Boolean = false
    private var remoteService: Messenger? = null

    private var remoteConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 1.Create a Messenger instance from the binder that was passed from the server
            remoteService = Messenger(service)
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            remoteService = null
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent("com.wifill.eatservice.ACTION_BIND")
        bindService(intent, remoteConnection, Context.BIND_AUTO_CREATE) // Bind to the remote service
    }

    fun onSendClick(v: View) {
      // Send a Message when the button is clicked
        if (bound) {
            remoteService?.send(Message.obtain(null, 2, 0, 0))
        }
    }
}