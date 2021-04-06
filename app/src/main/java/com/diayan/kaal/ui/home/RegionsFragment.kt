package com.diayan.kaal.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.diayan.kaal.R
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
    ): View {

        regionsViewModel = injectViewModel(viewModelFactory)
        val binding = FragmentRegionsBinding.inflate(inflater)

        val manager = GridLayoutManager(activity, 2)
        binding.eventsRecyclerView.layoutManager = manager
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position) {
                0 -> 2
                else -> 1
            }
        }

        val adapter = EventsAdapter(EventsAdapter.RegionsClickListener { regionId ->
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                // Now we provide a list of Pair items which contain the view we can transitioning
                // from, and the name of the view it is transitioning to, in the launched activity
                Pair(
                    view?.findViewById(R.id.region_cover_imageView),
                    DetailActivity.VIEW_NAME_HEADER_IMAGE
                )
            )
            // Now we can start the Activity, providing the activity options as a bundle
            val intent = Intent(context, DetailActivity::class.java)
            ActivityCompat.startActivity(requireContext(), intent, activityOptions.toBundle())
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