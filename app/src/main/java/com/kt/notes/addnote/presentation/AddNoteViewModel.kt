package com.kt.notes.addnote.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kt.notes.addnote.model.AddNoteState
import com.kt.notes.common.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _addNoteState: MutableLiveData<AddNoteState> = MutableLiveData(AddNoteState())
    val addNoteState: LiveData<AddNoteState> = _addNoteState

    fun onTitleTextFieldValueChanged(newValue: String) {
        _addNoteState.value = _addNoteState.value!!.copy(title = newValue)
    }

    fun onDescriptionTextFieldValueChanged(newValue: String) {
        _addNoteState.value = _addNoteState.value!!.copy(description = newValue)
    }

    fun addNote() {
        if (_addNoteState.value!!.title.isEmpty() && _addNoteState.value!!.description.isEmpty()) return
        val document = firestore.collection("notes").document()
        val note = Note(
            title = _addNoteState.value!!.title,
            description = _addNoteState.value!!.description,
            userId = firebaseAuth.currentUser?.uid!!,
            id = document.id,
        )
        document.set(note)
            .addOnSuccessListener {
                Log.d("logCheck", "DocumentSnapshot added")
            }
            .addOnFailureListener { e ->
                Log.w("logCheck", "Error adding document", e)
            }
    }
}