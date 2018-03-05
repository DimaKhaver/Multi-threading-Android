package com.example.dima.androidmultithreading.asynchronoustechniques.ContentProvidersAsyncQueryHandler

import android.app.ExpandableListActivity
import android.content.AsyncQueryHandler
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.CursorTreeAdapter
import android.widget.SimpleCursorTreeAdapter
import android.content.ContentUris




/**
AsyncQueryHandler cannot be used for asynchronous interaction with the SQLite DB directly. Instead,
the DB should be wrapped in a ContentProvider that can be accessed through a ContentResolver.

The AsyncQueryHandler holds a ContentResolver , an execution environment for
background processing, and handles the thread communication to and from the back‐
ground thread. When one of the provider requests ( startQuery , startInsert , start
Delete or startUpdate ) is invoked, a Message with the request is added to a Message
Queue processed by one background thread.
*/
class ExpandableContactListActivity : ExpandableListActivity() {

    companion object {
      //Define tokens that represent request types that the QueryHandler handles:
      //one for contact name requests and one for phone number requests
        private val GROUP_ID_COLUMN_INDEX = 0
        private val TOKEN_GROUP = 0
        private val TOKEN_CHILD = 1

        private val CONTACTS_PROJECTION = arrayOf<String>(
                ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME)

        private val PHONE_NUMBER_PROJECTION = arrayOf(Phone._ID, Phone.NUMBER)
    }

    private var mQueryHandler: QueryHandler? = null
    private var mAdapter: CursorTreeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up our adapter
        mAdapter = MyExpandableListAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                android.R.layout.simple_expandable_list_item_1,
                arrayOf<String>(ContactsContract.Contacts.DISPLAY_NAME), // Name for group layouts
                intArrayOf(android.R.id.text1),
                arrayOf(Phone.NUMBER), // Number for child layouts
                intArrayOf(android.R.id.text1))

        setListAdapter(mAdapter)
        mQueryHandler = QueryHandler(this, mAdapter as MyExpandableListAdapter)

      // Query for people
      // Start an asynchronous query for contact names.
        mQueryHandler!!.startQuery(TOKEN_GROUP,
                null,
                ContactsContract.Contacts.CONTENT_URI,
                CONTACTS_PROJECTION,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC");
    }

    override fun onDestroy() {
        super.onDestroy()
      //Cancel pending provider operations if the Activity is destroyed
        mQueryHandler?.cancelOperation(TOKEN_GROUP);
        mQueryHandler?.cancelOperation(TOKEN_CHILD);
        mAdapter?.changeCursor(null);
        mAdapter = null;
    }

    private class QueryHandler : AsyncQueryHandler {
        private var mAdapter: CursorTreeAdapter? = null

        constructor(context: Context, adapter: CursorTreeAdapter) : super(context.getContentResolver()) {
            mAdapter = adapter
        }

        override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
            when (token) {
              //Receive the result for the contact name requested in mQueryHandler.startQuery.
              //The adapter initiates a consecutive query on the child cursor—i.e., the phone numbers
                TOKEN_GROUP -> mAdapter?.setGroupCursor(cursor)
                TOKEN_CHILD -> {
                  //Receive result for the phone number query, with a cookie that identifies the
                  //contact it belongs to
                    val groupPosition = cookie as Int
                    mAdapter?.setChildrenCursor(groupPosition, cursor)
                }
            }
        }
    }

    public class MyExpandableListAdapter : SimpleCursorTreeAdapter {
        // The constructor does not take a Cursor.
        // This is done to avoid querying the database on the main thread.

        constructor(context: Context, groupLayout: Int, childLayout: Int, groupFrom: Array<String>,
                    groupTo: IntArray, childrenFrom: Array<String>, childrenTo: IntArray):
            super(context, null, groupLayout, groupFrom, groupTo, childLayout, childrenFrom, childrenTo)

        override fun getChildrenCursor(groupCursor: Cursor?): Cursor? {
            // Given the group, we return a cursor for all the children within that group
            // Return a cursor that points to this contact's phone numbers
            val builder = ContactsContract.Contacts.CONTENT_URI.buildUpon()
            groupCursor?.getLong(
                    GROUP_ID_COLUMN_INDEX)?.let { ContentUris.appendId(builder, it) }
            builder.appendEncodedPath(ContactsContract.Contacts.Data.CONTENT_DIRECTORY)
            val phoneNumbersUri = builder.build()
/*
          //Start asynchronous query for phone numbers that belong to the contacts
            mQueryHandler.startQuery(TOKEN_CHILD,
                    groupCursor.getPosition(),
                    phoneNumbersUri,
                    PHONE_NUMBER_PROJECTION,
                    Phone.MIMETYPE + "=?",
                    new String[] { Phone.CONTENT_ITEM_TYPE },
                    null);
*/
            return null
        }
    }

}