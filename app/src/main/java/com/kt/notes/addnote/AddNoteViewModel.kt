package com.kt.notes.addnote

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kt.notes.model.Note

class AddNoteViewModel @ViewModelInject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel() {

    fun addNote(title: String, description: String) {
        if (title.isEmpty() && description.isEmpty()) return

        val document = firestore.collection("notes").document()
        val note = Note(title, description, firebaseAuth.currentUser?.uid!!, document.id, null)

        document.set(note)
            .addOnSuccessListener {
                Log.d("logCheck", "DocumentSnapshot added")
            }
            .addOnFailureListener { e ->
                Log.w("logCheck", "Error adding document", e)
            }
    }

}