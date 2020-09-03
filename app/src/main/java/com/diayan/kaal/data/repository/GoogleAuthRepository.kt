package com.diayan.kaal.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class GoogleAuthRepository @Inject constructor(val firebaseAuth: FirebaseAuth) {


    val TAG = "AuthRepository"

    fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential): MutableLiveData<com.diayan.kaal.data.model.User> {

        val authenticatedUserMutableLiveData: MutableLiveData<com.diayan.kaal.data.model.User> = MutableLiveData()

        firebaseAuth.signInWithCredential(googleAuthCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val isNewUser = task.result?.additionalUserInfo?.isNewUser
                    val firebaseUser = firebaseAuth.currentUser

                    if (firebaseUser != null){
                        val uid = firebaseUser.uid
                        val name = firebaseUser.displayName!!
                        val email = firebaseUser.email!!
                        val user = com.diayan.kaal.data.model.User(uid, name, email,
                            isAuthenticated = false,
                            isNew = false,
                            isCreated = false
                        )
                        user.isNew = isNewUser!!
                        authenticatedUserMutableLiveData.value = user
                    }
                    val user = firebaseAuth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Log.e(TAG, task.exception?.message)
                }

                // ...
            }

        return authenticatedUserMutableLiveData
    }
}