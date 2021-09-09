package com.kt.notes.common.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kt.notes.common.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private lateinit var note: Note

    fun setNote(note: Note) {
        savedStateHandle.set("note", note)
        this.note = note
    }

    fun getNote(): Note {
        return savedStateHandle.get<Note>("note") ?: note
    }

    fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}