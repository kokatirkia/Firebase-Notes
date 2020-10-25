package com.kt.notes.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Note(
    var title: String = "",
    var description: String = "",
    val userId: String = "",
    val id:String="",
    @ServerTimestamp val timeStamp: Timestamp? = null
)