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
import androidx.recyclerview.widget.GridLayoutManager
import com.diayan.kaal.databinding.FragmentEventsBinding
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel
import kotlinx.android.synthetic.main.fragment_events.*
import javax.inject.Inject


class EventsFragment : Fragment(), Injectable {

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        eventsViewModel = injectViewModel(viewModelFactory)
        val binding = FragmentEventsBinding.inflate(inflater)

        val header: View = LayoutInflater.from(context).inflate(
            com.diayan.kaal.R.layout.header,
            events_recyclerView,
            false
        )

        with(eventsViewModel) {
            getEvents()
            eventsLiveData.observe(viewLifecycleOwner, Observer {
                Log.e("Firebase Event:::", it.toString())
                val layoutManager = GridLayoutManager(context, 2)
                events_recyclerView.layoutManager = layoutManager
                val adapter = ExperimentalAdapter(it, header)
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (adapter.isHeader(position)) layoutManager.spanCount else 1
                    }
                }
                events_recyclerView.adapter = adapter
            })
        }


/*
        val manager = GridLayoutManager(activity, 2)
        binding.eventsRecyclerView.layoutManager = manager
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position) {
                0 -> 1
                else -> 2
            }
        }

        val adapter = EventsAdapter(EventClickListener { eventId ->
            if (eventId.id == 1) {
            }
        })
        eventsViewModel.eventsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
               // adapter.addHeaderAndSubmitList(it)
            }
        })*/
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // AndroidSupportInjection.
    }
}