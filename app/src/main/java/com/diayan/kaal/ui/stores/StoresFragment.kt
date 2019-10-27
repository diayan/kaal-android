package com.diayan.kaal.ui.stores

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

class StoresFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var storesViewModel: StoresViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        storesViewModel = injectViewModel(viewModelFactory)
        val root = inflater.inflate(R.layout.fragment_stores, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        storesViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}