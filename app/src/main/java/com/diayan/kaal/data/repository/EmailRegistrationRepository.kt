package com.diayan.kaal.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class EmailRegistrationRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    val TAG = "EmailPasswordRepository"

    private val loggedOutLiveData = MutableLiveData<Boolean>()

    private val userLiveData = MutableLiveData<FirebaseUser>()

    init {
        userLiveData
    }

    fun login(email: String?, password: String?) {
        firebaseAuth.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    Log.w(TAG, "Login failure", task.exception)
                }
            }
    }

    fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                    //Log.w(TAG, "Current user: " + firebaseAuth.currentUser!!.displayName)

                } else {
                    Log.w(TAG, "Registration failed: ", task.exception)
                }
            }
    }

    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }

    /* suspend fun getUser(user: String): User {
         return User("user", "user", "email", false, true, true)
     }*/
}