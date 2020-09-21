package com.diayan.kaal.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.diayan.kaal.data.model.User
import com.diayan.kaal.data.repository.EmailRegistrationRepository
import com.diayan.kaal.util.DOMAIN_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationRepository: EmailRegistrationRepository
) : ViewModel() {

    private val TAG = "RegistrationViewModel"

    private var _authenticatedUserMutableLiveData = MutableLiveData<User>()

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    val authenticatedUserLiveData: LiveData<User>
        get() = _authenticatedUserMutableLiveData

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     */
    init {
        //getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    fun signInWithEmailAndPassword(email: String?, password: String?){
        registrationRepository.emailAndPasswordRegistration(email,password)
    }

    val user = liveData {
        emit(registrationRepository.getUser("user"))
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

    fun registerNewEmail(email: String, password: String?) {

    }
}