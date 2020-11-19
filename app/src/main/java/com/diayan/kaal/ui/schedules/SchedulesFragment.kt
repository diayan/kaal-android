package com.diayan.kaal.ui.schedules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.diayan.kaal.databinding.FragmentSchedulesBinding
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel
import javax.inject.Inject

class SchedulesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SchedulesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        val binding = FragmentSchedulesBinding.inflate(inflater)

        val  actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = "Scheduled Trips"

        val manager = LinearLayoutManager(activity)
        binding.schedulesRecyclerView.layoutManager = manager

        val adapter = SchedulesAdapter(SchedulesAdapter.TripClickListener { tripId ->
        })

        binding.schedulesRecyclerView.adapter = adapter

        with(viewModel) {
            getScheduledTrips()
            scheduledTripsLiveData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.addHeaderAndSubmitList(it)
                }
            })
        }
        return binding.root
    }
}