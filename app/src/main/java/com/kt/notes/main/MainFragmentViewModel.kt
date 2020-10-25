package com.kt.notes.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.kt.notes.model.Note

class MainFragmentViewModel @ViewModelInject constructor(
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore
) : ViewModel() {

    private val notesLiveData = MutableLiveData<List<Note>>()
    private lateinit var registration: ListenerRegistration

    init {
        fetchNotes()
    }

    fun getNotes(): LiveData<List<Note>> = notesLiveData

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
                notesLiveData.postValue(notes)
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