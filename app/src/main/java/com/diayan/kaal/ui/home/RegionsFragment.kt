package com.diayan.kaal.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.diayan.kaal.databinding.FragmentRegionsBinding
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel
import com.diayan.kaal.ui.detail.DetailActivity
import javax.inject.Inject

class RegionsFragment : Fragment(), Injectable {

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var regionsViewModel: RegionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        regionsViewModel = injectViewModel(viewModelFactory)
        val binding = FragmentRegionsBinding.inflate(inflater)

  /*      val header: View = LayoutInflater.from(context).inflate(
            com.diayan.kaal.R.layout.item_regions_header,
            events_recyclerView,
            false
        )

        with(regionsViewModel) {
            getEvents()
            regionsLiveData.observe(viewLifecycleOwner, Observer {
                Log.e("Firebase Regions:::", it.toString())
                val layoutManager = GridLayoutManager(context, 2)
                events_recyclerView.layoutManager = layoutManager
                val adapter = RegionsAdapter(it, header)
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (adapter.isHeader(position)) layoutManager.spanCount else 1
                    }
                }
                events_recyclerView.adapter = adapter
            })
        }*/


        val manager = GridLayoutManager(activity, 2)
        binding.eventsRecyclerView.layoutManager = manager
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position) {
                0 -> 2
                else -> 1
            }
        }

        val adapter = EventsAdapter(EventsAdapter.RegionsClickListener { regionId ->
            val intent = Intent(context, DetailActivity::class.java)
            startActivity(intent)
        })

        with(regionsViewModel) {
            getEvents()
            regionsViewModel.regionsLiveData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    Log.e("Firebase Regions:::", it.toString())
                    adapter.addHeaderAndSubmitList(it)
                }
            })
            binding.eventsRecyclerView.adapter = adapter
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // AndroidSupportInjection.
    }
}