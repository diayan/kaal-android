package com.diayan.kaal.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*


class FirebaseQueryLiveData(ref: DatabaseReference) : LiveData<DataSnapshot>() {

    private var query: Query = ref
    private val listener = MyValueEventListener()

    fun FirebaseQueryLiveData(query: Query){
        this.query = query
    }

    override fun onActive() {
        query.addValueEventListener(listener)
    }

    override fun onInactive() {
        query.removeEventListener(listener)
    }

    private inner class MyValueEventListener : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            Log.d("dataSnapshot ", dataSnapshot.toString() )
            value = dataSnapshot
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e(LOG_TAG, "Can't listen to query $query", databaseError.toException())
        }
    }

    companion object {
        private const val LOG_TAG = "FirebaseLiveDataQuery"
    }

}