package com.diayan.kaal.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.model.User
import com.diayan.kaal.data.repository.EmailRegistrationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationRepository: EmailRegistrationRepository
) : ViewModel() {

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
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        //getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    fun signInWithEmailAndPassword(email: String?, password: String?){


        _authenticatedUserMutableLiveData = registrationRepository.emailAndPasswordRegistration(email,password)

    }
}