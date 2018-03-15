package com.example.dima.multithreadinginandroidandjava.androidmultithreading.asynchronoustechniques.ContentProvidersAsyncQueryHandler

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.CancellationSignal

/**
The access methods originate from the most common use case for providers: to expose
data stored in a SQLite database across application boundaries. SQLite databases are
private to applications but can be exposed to other applications through the Content
Provider class, providing application entry points that are registered at application
installation.
ContentProviders cannot control how many clients will access the data or if it can
happen simultaneously. The encapsulated data of a provider can be accessed concurrently
from multiple threads, which can both read and write to the data set.
Thread safety can be achieved by applying synchronization to the query, insert, update and delete data
access methods, but it is required only if the data source needs it. SQLite database access,
for example, is thread safe in itself because the transaction model of the database is
sequential, so that the data can not be corrupted by concurrent accesses.
It should not be executed on UI thread because it may become a long task that can delay UI rendering
*/
class EatContentProvider : ContentProvider() {

    companion object {
        private val STRING_URI = "content://com.eat.provider/resource"
        public var CONTENT_URI= Uri.parse(STRING_URI)
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(uri: Uri?, projection: Array<out String>?, queryArgs: Bundle?, cancellationSignal: CancellationSignal?): Cursor {
        var cursor: Cursor? = null
        // Read data source
        return cursor!!
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}