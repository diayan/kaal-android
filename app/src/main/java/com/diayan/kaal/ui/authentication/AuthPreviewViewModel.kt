package com.diayan.kaal.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.model.User
import com.diayan.kaal.data.repository.GoogleAuthRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthCredential
import javax.inject.Inject

class AuthPreviewViewModel @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository
): ViewModel() {

    private var _authenticatedUserLiveData = MutableLiveData<User>()
    val authenticatedUserLiveData: LiveData<User> = _authenticatedUserLiveData

    fun signInWithGoogle(authCredential: AuthCredential) {
        _authenticatedUserLiveData = googleAuthRepository.firebaseSignInWithGoogle(authCredential)
    }
}