package com.diayan.kaal.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.diayan.kaal.R
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel
import com.google.firebase.database.*
import com.google.gson.Gson
import javax.inject.Inject

class EventsFragment : Fragment(), Injectable {

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var eventsViewModel: EventsViewModel
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.getReference("events")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        eventsViewModel = injectViewModel(viewModelFactory)

        //val eventsLivedData = eventsViewModel.getEventsLiveData()
        //Log.d("event value: ", Gson().toJson(eventsLivedData))

       /* eventsLivedData.observe(this, object:Observer() {

        })*/

        /*myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(FirebaseEvents::class.java)
                Log.d( "Value is: ", Gson().toJson( value))
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Failed to read value.", error.toException())
            }
        })*/

        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
       // AndroidSupportInjection.
    }
}