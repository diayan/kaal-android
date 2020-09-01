package com.diayan.kaal.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.diayan.kaal.databinding.AuthPreviewFragmentBinding

class AuthPreviewFragment : Fragment() {

    private lateinit var viewModel: AuthPreviewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //TODO: how to add drawables to material buttons

        val binding = AuthPreviewFragmentBinding.inflate(inflater)
        binding.previewLogin.setOnClickListener { view: View? ->
            view?.findNavController()?.navigate(
                AuthPreviewFragmentDirections.actionAuthPreviewFragmentToSignInFragment()
            )
        }

        binding.previewSignUpButton.setOnClickListener { view: View? ->
            view?.findNavController()?.navigate(
                AuthPreviewFragmentDirections.actionAuthPreviewFragmentToRegistrationFragment()
            )
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthPreviewViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }
}