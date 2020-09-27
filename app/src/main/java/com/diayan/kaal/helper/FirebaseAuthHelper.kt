package com.diayan.kaal.helper

import com.google.firebase.auth.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await

class FirebaseAuthHelper {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun currentUser() = firebaseAuth.currentUser

    suspend fun loginWith(email: String, password: String): FirebaseUser? {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return currentUser() ?: throw FirebaseAuthException("", "")
    }

    suspend fun createUserWith(email: String, password: String): FirebaseUser? {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return currentUser() ?: throw  FirebaseAuthException("", "")
    }

    suspend fun setDisplayName(fullName: String) =
        currentUser()?.let {
            val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName(fullName).build()
            it.updateProfile(profileUpdate).await()
        }

/*    suspend fun loginWithFacebook(token: AccessToken): FirebaseUser? {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential).await()
        return currentUser() ?: throw FirebaseAuthException("", "")
    }

    suspend fun loginWithTwitter(session: TwitterSession): FirebaseUser? {
        val credential =
            TwitterAuthProvider.getCredential(session.authToken.token, session.authToken.secret)
        firebaseAuth.signInWithCredential(credential).await()
        return currentUser() ?: throw FirebaseAuthException("", "")
    }*/

    fun sendPasswordResetEmailAsync(email: String): Deferred<Void> =
        firebaseAuth.sendPasswordResetEmail(email).asDeferred()

    fun logout() = firebaseAuth.signOut()
}
