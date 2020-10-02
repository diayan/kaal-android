package com.diayan.kaal.ui.authentication

import android.app.Activity
import android.os.Bundle
import android.util.Log
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
import com.diayan.kaal.databinding.SignInFragmentBinding
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel
import com.diayan.kaal.helper.SharedPrefManager
import com.diayan.kaal.util.IntentUtil
import com.diayan.kaal.util.Utils
import javax.inject.Inject

class SignInFragment : Fragment(), Injectable {

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AuthViewModel

    private val sharedPreferencesManager: SharedPrefManager by lazy {
        sharedPreferencesManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)
        val binding = SignInFragmentBinding.inflate(inflater)


        viewModel.formState.observe(viewLifecycleOwner, Observer {
            val formState = it ?: return@Observer

            // disable login button unless both username / password is valid
          //  binding.signInButton.isEnabled = formState.isDataValid

            if (formState.emailError != null) {
                binding.emailEditText.error = getString(formState.emailError)
            }

            if (formState.passwordError != null) {
                binding.passwordEditText.error = getString(formState.passwordError)
            }

            viewModel.formResult.observe(viewLifecycleOwner, Observer {
                Log.e("FormResult", it.toString())
                val formResult = it ?: return@Observer
                binding.loader.visibility = View.GONE
                if (formResult.error != null) {
                    showLoginFailure(formResult.error)
                }
                if (formResult.success != null) {
                    openNextActivity()
                }
            })

            binding.emailEditText.doAfterTextChanged {
                viewModel.loginDataChanged(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            }

            binding.passwordEditText.apply {

                doAfterTextChanged {
                    viewModel.loginDataChanged(
                        binding.emailEditText.text.toString(),
                        binding.passwordEditText.text.toString()
                    )
                }

                setOnEditorActionListener { textView, i, keyEvent ->
                    when (i) {
                        EditorInfo.IME_ACTION_DONE ->
                            viewModel.loginWith(
                                binding.emailEditText.text.toString(),
                                binding.passwordEditText.text.toString()
                            )
                    }
                    false
                }
            }

            binding.signInButton.setOnClickListener {
                binding.loader.visibility = View.VISIBLE
                Utils.hideKeyboard(activity as MainActivity)

                Log.d("SignInFragment: ", "sign in button clicked")
                viewModel.loginWith(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            }

            //forgot password button comes here
            //forgot_password_textView.setOnClickListener { launchActivity<ForgotPasswordActivity> {} }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Sign In"
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