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
import javax.inject.Inject

class EventsFragment : Fragment(), Injectable {

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        eventsViewModel = injectViewModel(viewModelFactory)

        with(eventsViewModel) {
            getEvents()
            eventsLiveData.observe(this@EventsFragment, Observer {
                Log.e("Firebase Event:::", it.toString())
            })

        }
        //val eventsLivedData = eventsViewModel.getEventsLiveData()
        //Log.d("event value: ", Gson().toJson(eventsLivedData))

       /* eventsLivedData.observe(this, object:Observer() {

        })*/
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
       // AndroidSupportInjection.
    }
}