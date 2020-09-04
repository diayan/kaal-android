package com.diayan.kaal.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.diayan.kaal.data.model.User
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class EmailRegistrationRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    val TAG = "EmailPasswordRepository"

    fun emailAndPasswordRegistration(email: String?, password: String?): MutableLiveData<User> {
        val authenticatedUserMutableLiveData: MutableLiveData<User> = MutableLiveData()

        firebaseAuth.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val isNewUser = task.result?.additionalUserInfo?.isNewUser

                    Log.d(TAG, "signInWithEmail:success")
                    val firebaseUser = firebaseAuth.currentUser

                    if (firebaseUser != null) {
                        val uid = firebaseUser.uid
                        val name = firebaseUser.displayName!!
                        val userEmail = firebaseUser.email!!
                        val user = User(
                            uid, name, userEmail,
                            isAuthenticated = false,
                            isNew = false,
                            isCreated = false
                        )
                        user.isNew = isNewUser!!
                        authenticatedUserMutableLiveData.value = user
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        // updateUI(null)
                        // ...
                    }

                    // ...
                }
            }
        return authenticatedUserMutableLiveData
    }


}