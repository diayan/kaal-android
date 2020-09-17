package com.diayan.kaal.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.diayan.kaal.databinding.RegistrationFragmentBinding
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel
import javax.inject.Inject

class RegistrationFragment : Fragment(), Injectable {

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)
        val binding = RegistrationFragmentBinding.inflate(inflater)

        viewModel.authenticatedUserLiveData.observe(viewLifecycleOwner, Observer {

        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Sign Up"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}