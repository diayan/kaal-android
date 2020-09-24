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

/*    fun emailAndPasswordRegistration(email: String?, password: String?): MutableLiveData<User> {
        val newUserMutableLiveData = MutableLiveData<User>()

        firebaseAuth.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {

                    Log.d(TAG, "onComplete: onComplete ${task.isSuccessful}")

                    Log.d(TAG, "onComplete: AuthState ${firebaseAuth.currentUser!!.uid}")

                    //this line is important bcuz we want the user to be verified before they are signed in
                    firebaseAuth.signOut()

                    Log.d(TAG, "signInWithEmail:success")
                    val firebaseUser = firebaseAuth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "Unable to register user: ", task.exception)
                }
            }
    }*/

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