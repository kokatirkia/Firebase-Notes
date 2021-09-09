package com.kt.notes.notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.kt.notes.common.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore
) : ViewModel() {

    private lateinit var registration: ListenerRegistration
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        registration = firestore.collection("notes")
            .whereEqualTo("userId", firebaseAuth.currentUser?.uid)
            .orderBy("timeStamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                Log.w("logCheckdfg", "called")

                if (e != null) {
                    Log.w("logCheck", "listen:error", e)
                    return@addSnapshotListener
                }
                val notes = arrayListOf<Note>()
                for (dc in snapshots!!.documents) {
                    Log.d("logCheck", "New city: ${dc.data}")
                    val note = dc.toObject(Note::class.java)
                    note?.let { notes.add(it) }
                }
                _notes.postValue(notes)
            }
    }

    fun deleteNote(note: Note) {
        firestore.collection("notes")
            .document(note.id)
            .delete()
            .addOnSuccessListener {
                Log.d("logCheck", "note deleted")
            }
            .addOnFailureListener {
                Log.d("logCheck", "delete failed")
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    override fun onCleared() {
        super.onCleared()
        registration.remove()
    }
}