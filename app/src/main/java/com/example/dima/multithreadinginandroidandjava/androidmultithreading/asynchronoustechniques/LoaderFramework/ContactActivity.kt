package com.example.dima.multithreadinginandroidandjava.androidmultithreading.asynchronoustechniques.LoaderFramework

import android.app.ListActivity
import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.SimpleCursorAdapter


/**
The CursorLoader can be used only with Cursor objects delivered
from content providers, not those that come from SQLite databases

Loader lifecycle
void startLoading() -> void onStartLoading()
void stopLoading() -> void onStopLoading()
void reset() -> void onReset()
void abandon() -> void onAbandon()

To avoid leaking outer class objects referenced from inner classes — typically
Activity and Fragment — a custom loader has to be declared as a static or external class,
otherwise RuntimeException is thrown when the loader is returned from onCreateLoader
 */
class ContactActivity : ListActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private val CONTACT_NAME_LOADER_ID = 0
        // Projection that defines just the contact display name
        // The loader only queries the display name of the contacts.
        val CONTACTS_SUMMARY_PROJECTION =
                arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME)
    }

    var adapter: SimpleCursorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        // Initiate a loader with the LoaderManager , which is followed by a callback to onCreateLoader
        loaderManager.initLoader(CONTACT_NAME_LOADER_ID, null, this)
    }

    private fun initAdapter() {
        adapter = SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, null,
                arrayOf(ContactsContract.Contacts.DISPLAY_NAME),
                intArrayOf(android.R.id.text1), 0)
        listAdapter = adapter
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        // The first callback. The Activity creates a loader and hands it over to the platform.
        return CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                CONTACTS_SUMMARY_PROJECTION, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC")
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        // Whenever data has finished loading on a background thread, the data is served on the UI thread
        adapter?.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        // When the loader is reset, the last callback is invoked and the
        // Activity releases the references to the loader data
        adapter?.swapCursor(null)
    }
}