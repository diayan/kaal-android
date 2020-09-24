package com.diayan.kaal.ui.authentication

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.diayan.kaal.databinding.RegistrationFragmentBinding
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel
import kotlinx.android.synthetic.main.registration_fragment.*
import javax.inject.Inject

class RegistrationFragment : Fragment(), Injectable {
    val TAG = "RegistrationFragment"

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)
        val binding = RegistrationFragmentBinding.inflate(inflater)

        /*  viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
              Log.d(TAG, it.displayName!!.toString())
          })*/

        binding.signUpButton.setOnClickListener {

            if (!viewModel.isEmpty(emailEditText.text.toString()) && !isEmpty(firstNameEditText.text.toString())
                && !isEmpty(lastNameEditText.text.toString()) && !isEmpty(passwordEditText.text.toString())
            ) {
                if (viewModel.isValidDomain(emailEditText.text.toString())) {

                    //TODO: Make editText boxes show error if they are empty
                    viewModel.signInWithEmailAndPassword(
                        email = emailEditText.text.toString(),
                        password = passwordEditText.text.toString()
                    )

                } else {
                    Toast.makeText(activity, "Email not valid", Toast.LENGTH_LONG).show()
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Sign Up"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}