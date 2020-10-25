package com.kt.notes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kt.notes.model.Note

class SharedViewModel @ViewModelInject constructor(
    val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private lateinit var note: Note
    fun setNote(note: Note) {
        this.note = note
    }

    fun getNote(): Note {
        return note
    }

    fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}