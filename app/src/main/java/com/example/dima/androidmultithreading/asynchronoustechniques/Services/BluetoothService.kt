package com.example.dima.androidmultithreading.asynchronoustechniques.Services

import android.annotation.SuppressLint
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.IOException
import java.util.*

/**
 * Created by Dima on 3/3/2018.
 */
class BluetoothService : Service() {
    companion object {
        public val COMMAND_KEY = "command_key"
        public val COMMAND_START_LISTENING = "command_start_discovery"
        private val MY_UUID: UUID? = null
        private val SDP_NAME: String? = null
    }

    private var adapter: BluetoothAdapter? = null
    private var serverSocket: BluetoothServerSocket? = null
    private var listening = false
    private var listeningThread: Thread? = null


    override fun onBind(intent: Intent) : IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        adapter = BluetoothAdapter.getDefaultAdapter()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.getStringExtra(COMMAND_KEY)?.equals(COMMAND_START_LISTENING)!! && !listening)
            startListening()

        return START_REDELIVER_INTENT
    }

    @SuppressLint("MissingPermission")
    private fun startListening() {
        listening = true
        listeningThread = Thread(Runnable {
            var socket: BluetoothSocket? = null
            try {
                serverSocket = adapter?.listenUsingInsecureRfcommWithServiceRecord(
                                SDP_NAME, MY_UUID)
                socket = serverSocket!!.accept()

                if (socket != null) {
                    // handle BT connection
                }
            } catch (e: IOException) {
                Log.d(TAG, "Server socket closed");
            }
        })
        listeningThread?.start()
    }

    private fun stopListening() {
        listening = false;
        try {
            serverSocket?.close()
        } catch (e: IOException) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopListening()
    }
}