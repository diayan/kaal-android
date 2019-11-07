package com.diayan.kaal.data

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot

class FirebaseQueryLiveData(ref: DatabaseReference) : LiveData<DataSnapshot>() {

    private val query: Query = ref
    private val listener = MyValueEventListener()

    override fun onActive() {

        Log.d(LOG_TAG, "onActive")
        query.addValueEventListener(listener)
    }

    override fun onInactive() {
        Log.d(LOG_TAG, "onInactive")
        query.removeEventListener(listener)
    }

    private inner class MyValueEventListener : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e(LOG_TAG, "Can't listen to query $query", databaseError.toException())
        }
    }

    companion object {
        private const val LOG_TAG = "FirebaseLiveDataQuery"
    }

   /* private val query: Query = ref
    private val handler = Handler()
    private val listener = MyValueChildEventListener()
    private val valueListener = MyValueEventListener()
    private val mQueryValuesList = ArrayList<Any>()


    private var listenerRemovePending = false

    private val removeListener = Runnable {
        query.removeEventListener(valueListener)
        listenerRemovePending = false
    }

    override fun onActive() {
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener)
        } else {
            query.addValueEventListener(valueListener)
        }
        listenerRemovePending = false
    }

    override fun onInactive() {
        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000)
        listenerRemovePending = true
    }

    private inner class MyValueEventListener : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e(LOG_TAG, "Cannot listen to query $query", databaseError.toException())
        }
    }

    private inner class MyValueChildEventListener : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
            value = dataSnapshot
            for (snap in dataSnapshot.children) {
                val msg = snap.getValue(SomeClass.javaClass)
                mQueryValuesList.add(msg!!)
            }
        }

        override fun onChildRemoved(p0: DataSnapshot) {
        }
    }

    companion object {
        private const val LOG_TAG = "FirebaseLiveDataQuery"
    }*/
}