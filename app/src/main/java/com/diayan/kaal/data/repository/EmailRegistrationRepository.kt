package com.diayan.kaal.data.repository

import android.util.Log
import com.diayan.kaal.data.model.User
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class EmailRegistrationRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    val TAG = "EmailPasswordRepository"

    fun emailAndPasswordRegistration(email: String?, password: String?){

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
    }

    suspend fun getUser(user: String): User {
        return User("user", "user", "email", false, true, true)
    }
}