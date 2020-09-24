package com.diayan.kaal.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.diayan.kaal.data.repository.EmailRegistrationRepository
import com.diayan.kaal.util.DOMAIN_NAME
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationRepository: EmailRegistrationRepository
) : ViewModel() {

    private val TAG = "RegistrationViewModel"

    private var _userMutableLiveData = MutableLiveData<FirebaseUser>()

    val userLiveData: LiveData<FirebaseUser>
        get() = _userMutableLiveData

    init {
        _userMutableLiveData = registrationRepository.getUserLiveData()
    }

    fun signInWithEmailAndPassword(email: String, password: String){
        registrationRepository.register(email, password)
    }

    val user = liveData {
        emit(registrationRepository.getUserLiveData())
    }


















    fun isEmpty(string: String): Boolean {
        return string == ""
    }

    /*
    * This code should be used if you want to restrict the email domain providers to some specific ones.
    * eg. gmail.com if Gmail, outlook.com if outlook*/
    fun isValidDomain(email: String): Boolean {
        Log.d(TAG, "isValidDomain: verifying email has valid domain: $email")
        val domain = email.substring(email.indexOf("@") + 1).toLowerCase()
        Log.d(TAG, "isValidDomain: users domain: $domain")
        return domain == DOMAIN_NAME
    }
}