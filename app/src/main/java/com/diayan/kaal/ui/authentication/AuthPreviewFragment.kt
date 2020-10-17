package com.diayan.kaal.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.diayan.kaal.MainActivity
import com.diayan.kaal.R
import com.diayan.kaal.databinding.AuthPreviewFragmentBinding
import com.diayan.kaal.di.Injectable
import com.diayan.kaal.di.injectViewModel
import com.diayan.kaal.util.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.auth_preview_fragment.*
import javax.inject.Inject

class AuthPreviewFragment : Fragment(), Injectable {

    private val TAG = "AuthPreviewFragment"

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AuthViewModel

    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)

        initGoogleSignInClient()

        val binding = AuthPreviewFragmentBinding.inflate(inflater)

        binding.previewGoogleSignUp.setOnClickListener { view: View? ->
            signIn()
        }

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

    private fun initGoogleSignInClient() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!
    }

    fun signIn() {
        loaderImageView.visibility = View.VISIBLE
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                Log.d(TAG, "success!!" + account.displayName + account.email)
                loaderImageView.visibility = View.GONE

                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)

                //firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                loaderImageView.visibility = View.GONE

            }
        }
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