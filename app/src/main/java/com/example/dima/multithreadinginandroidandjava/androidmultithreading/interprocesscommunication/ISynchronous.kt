package com.example.dima.multithreadinginandroidandjava.androidmultithreading.interprocesscommunication

/**
 * Created by Dima on 2/10/2018.
 */
interface ISynchronous {
    public fun getThreadNameFast(): String
    public fun getThreadNameSlow(sleep: Long): String
    public fun getThreadNameBlocking(): String
    public fun getThreadNameUnblock(): String
}

