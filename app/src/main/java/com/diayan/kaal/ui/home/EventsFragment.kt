package com.diayan.kaal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.diayan.kaal.R
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel
import javax.inject.Inject

class EventsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        eventsViewModel = injectViewModel(viewModelFactory)

        val root = inflater.inflate(R.layout.fragment_events, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        eventsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}