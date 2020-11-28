package com.kt.notes

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kt.notes.model.Note

class SharedViewModel @ViewModelInject constructor(
    @Assisted val savedStateHandle: SavedStateHandle,
    val firebaseAuth: FirebaseAuth,
//    val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {
    private lateinit var note: Note
    fun setNote(note: Note) {
        savedStateHandle.set("note", note)
        this.note = note
    }

    fun getNote(): Note {
        savedStateHandle.get<Note>("note")?.let {
            return it
        }
        return note
    }

    fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}