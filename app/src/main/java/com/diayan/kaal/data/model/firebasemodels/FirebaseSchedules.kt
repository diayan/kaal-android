package com.diayan.kaal.data.model.firebasemodels

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
data class FirebaseSchedules (
    val id: Long = 0,
    val destination: String = "",
    //val date: Timestamp,
    val image: String? = "",
    val region: String = ""
): Parcelable