package com.diayan.kaal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

import com.diayan.kaal.R

class OnBoarding : Fragment() {

    companion object {
        fun newInstance() = OnBoarding()
    }

    private lateinit var viewModel: OnBoardingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OnBoardingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
