package com.diayan.kaal.ui.ar

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.diayan.kaal.R
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel

class ArFragment : Fragment(), Injectable {

    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ArViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        return inflater.inflate(R.layout.fragment_ar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
