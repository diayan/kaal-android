package com.diayan.kaal.ui.authentication

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diayan.kaal.R
import com.diayan.kaal.data.model.FormResult
import com.diayan.kaal.data.model.FormState
import com.diayan.kaal.data.repository.GoogleAuthRepository
import com.diayan.kaal.util.FirebaseAuthHelper
import com.diayan.kaal.util.ValidationUtil
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
    private val _formState = MutableLiveData<FormState>()

    //external immutable LiveData
    val formState: LiveData<FormState>
        get() = _formState

    //internal mutable LiveData
    private val _formResult = MutableLiveData<FormResult>()

    //external immutable LiveData
    val formResult: LiveData<FormResult>
        get() = _formResult

    private val _resetEmail = MutableLiveData<Job>()
    val resetEmail: LiveData<Job>
        get() = _resetEmail

    private val _isOnBoarded = MutableLiveData<Boolean>()
    val isOnBoarded: LiveData<Boolean>
        get() = _isOnBoarded

    private lateinit var resetJob: Job

    fun createUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                firebaseAuthHelper.createUserWith(email, password)
                    ?.let {
                        _formResult.value = FormResult(success = it)
                        firebaseAuthHelper.setDisplayName(name)
                    }
            } catch (ex: FirebaseAuthException) {
                Log.d(TAG, "Error creating user: ${ex.errorCode}")
            }
        }
    }

    fun loginWith(email: String, password: String) {
        viewModelScope.launch {
            try {
                firebaseAuthHelper.loginWith(email, password)
                    ?.let {
                        checkStatus(it.uid)
                        _formResult.value = FormResult(success = it)
                    }
            } catch (ex: FirebaseAuthException) {
                Log.d(TAG, "Error Logging user: ${ex.errorCode}")
                _formResult.value = FormResult(error = ex.localizedMessage)
            }
        }
    }


    //checks if user has been in signed in before i.e if user is a registered user of the app
    private suspend fun checkStatus(uid: String) {

    }

    fun registrationDataChanged(name: String, email: String, password: String) {
        if (TextUtils.isEmpty(name)) {
            _formState.value = FormState(nameError = R.string.prompt_empty_field)
        } else if (!ValidationUtil.isEmailValid(email)) {
            _formState.value = FormState(emailError = R.string.invalid_email)
        } else if (!ValidationUtil.isPasswordValid(password)) {
            _formState.value = FormState(passwordError = R.string.invalid_password)
        } else {
            _formState.value = FormState(isDataValid = true)
        }
    }

    fun validateEmail(email: String) {
        if (!ValidationUtil.isEmailValid(email)) {
            _formState.value = FormState(emailError = R.string.invalid_email)
        } else {
            _formState.value = FormState(isDataValid = true)
        }
    }

    fun passwordResetEmail(email: String) {
        _resetEmail.value = viewModelScope.launch {
            firebaseAuthHelper.sendPasswordResetEmailAsync(email).await()
        }
    }
}