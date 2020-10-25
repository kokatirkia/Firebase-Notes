package com.kt.notes.noteupdate

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kt.notes.model.Note

class NoteUpdateViewModel @ViewModelInject constructor(
    val firestore: FirebaseFirestore
) : ViewModel() {
    fun updateNote(note: Note) {
        Log.i("noteCheck", note.title)
        Log.i("noteCheck", note.description)
        firestore.collection("notes")
            .document(note.id)
            .update(
                "title", note.title,
                "description", note.description
            )
            .addOnSuccessListener {
                Log.i("noteCheck", "success")
            }
            .addOnFailureListener {
                Log.i("noteCheck", it.message!!)
            }
    }
}