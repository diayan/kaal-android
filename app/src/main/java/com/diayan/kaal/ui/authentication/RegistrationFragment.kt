package com.diayan.kaal.ui.authentication

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.diayan.kaal.MainActivity
import com.diayan.kaal.databinding.RegistrationFragmentBinding
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel
import com.diayan.kaal.util.IntentUtil
import javax.inject.Inject

class RegistrationFragment : Fragment(), Injectable {
    val TAG = "RegistrationFragment"

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)
        val binding = RegistrationFragmentBinding.inflate(inflater)

        viewModel.formState.observe(viewLifecycleOwner, Observer {
            val registerState = it ?: return@Observer

            binding.signUpButton.isEnabled = registerState.isDataValid

            if (registerState.nameError != null) {
                binding.nameEditText.error = getString(registerState.nameError)
            }

            if (registerState.emailError != null) {
                binding.emailEditText.error = getString(registerState.emailError)
            }

            if (registerState.passwordError != null) {
                binding.passwordEditText.error = getString(registerState.passwordError)
            }
        })

        viewModel.formResult.observe(viewLifecycleOwner, Observer {
            val formResult = it ?: return@Observer

            binding.progress.visibility = View.GONE

            if (formResult.error != null) {
                showLoginFailure(formResult.error)
            }

            if (formResult.success != null) {
                openNextActivity()
            }
        })

        binding.nameEditText.doAfterTextChanged {
            viewModel.registrationDataChanged(
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.emailEditText.doAfterTextChanged {
            viewModel.registrationDataChanged(
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.passwordEditText.apply {
            doAfterTextChanged {
                viewModel.registrationDataChanged(
                    binding.emailEditText.text.toString(),
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            }

            setOnEditorActionListener { textView, i, keyEvent ->
                when (i) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.createUser(
                            binding.nameEditText.text.toString(),
                            binding.emailEditText.text.toString(),
                            binding.passwordEditText.text.toString()
                        )
                }
                false
            }

            binding.signUpButton.setOnClickListener {
                binding.progress.visibility = View.VISIBLE
                viewModel.createUser(
                    binding.nameEditText.text.toString(),
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Sign Up"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openNextActivity() {
        IntentUtil.start(context, MainActivity::class.java)
        activity?.setResult(Activity.RESULT_OK)
        activity?.finish()
    }

    private fun showLoginFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }
}