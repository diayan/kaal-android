package com.diayan.kaal.data.model

import com.google.firebase.auth.FirebaseUser

data class FormResult(
    val success: FirebaseUser? = null,
    val error: String? = null
)