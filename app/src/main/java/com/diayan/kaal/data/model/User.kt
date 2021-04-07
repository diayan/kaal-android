package com.diayan.kaal.data.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var uid: String,
    var name: String,
    var email: String,
    @Exclude
    var isAuthenticated: Boolean,
    @Exclude
    var isNew: Boolean,
    @Exclude
    var isCreated: Boolean
) : Parcelable