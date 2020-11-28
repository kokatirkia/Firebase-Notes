package com.kt.notes.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    var title: String = "",
    var description: String = "",
    val userId: String = "",
    val id: String = "",
    @ServerTimestamp val timeStamp: Timestamp? = null
) : Parcelable