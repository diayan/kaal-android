package com.diayan.kaal.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diayan.kaal.data.model.FormResult
import com.diayan.kaal.data.model.FormState
import com.diayan.kaal.data.model.User
import com.diayan.kaal.data.repository.GoogleAuthRepository
import com.diayan.kaal.util.FirebaseAuthHelper
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository,
    private val firebaseAuthHelper: FirebaseAuthHelper
) : ViewModel() {

    val TAG = "AuthViewModel"

    //internal mutableLiveData
    private val _loginForm = MutableLiveData<FormState>()

    //external immutable LiveData
    val loginForm: LiveData<FormState>
        get() = _loginForm

    //internal mutable LiveData
    private val _loginResult = MutableLiveData<FormResult>()

    //external immutable LiveData
    val loginResult: LiveData<FormResult>
        get() = _loginResult

    private val _resetEmail = MutableLiveData<Job>()
    val resetEmail: LiveData<Job>
    get() = _resetEmail


    private lateinit var resetJob: Job


    fun createUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                firebaseAuthHelper.createUserWith(email, password)
                    .let {
                        _loginResult.value = FormResult(success = it)
                        firebaseAuthHelper.setDisplayName(name)
                    }
            }catch (ex: FirebaseAuthException) {
                Log.d(TAG, "Error creating user: ${ex.errorCode}")
            }
        }
    }












    private var _authenticatedUserLiveData = MutableLiveData<User>()
    val authenticatedUserLiveData: LiveData<User> = _authenticatedUserLiveData


    fun signInWithGoogle(authCredential: AuthCredential) {
        _authenticatedUserLiveData = googleAuthRepository.firebaseSignInWithGoogle(authCredential)
    }
}